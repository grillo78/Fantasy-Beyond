package grillo78.fantasy_beyond.phys;

import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;

public class OBB {

    public double cx, cy, cz;

    public double halfX, halfY, halfZ;

    public double[] axisX = new double[3];
    public double[] axisY = new double[3];
    public double[] axisZ = new double[3];

    private double pivotOffsetX = 0;
    private double pivotOffsetY = 0;
    private double pivotOffsetZ = 0;

    public OBB(double cx, double cy, double cz, double halfX, double halfY, double halfZ) {
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
        this.halfX = halfX;
        this.halfY = halfY;
        this.halfZ = halfZ;
        axisX = new double[]{1, 0, 0};
        axisY = new double[]{0, 1, 0};
        axisZ = new double[]{0, 0, 1};
    }

    public OBB(double cx, double cy, double cz, double halfX, double halfY, double halfZ, double[] axisX, double[] axisY, double[] axisZ) {
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
        this.halfX = halfX;
        this.halfY = halfY;
        this.halfZ = halfZ;
        this.axisX = normalize(axisX);
        this.axisY = normalize(axisY);
        this.axisZ = normalize(axisZ);
    }

    public void setPivotOffset(double localX, double localY, double localZ) {
        this.pivotOffsetX = localX;
        this.pivotOffsetY = localY;
        this.pivotOffsetZ = localZ;
    }

    public double[] getPivotOffset() {
        return new double[]{pivotOffsetX, pivotOffsetY, pivotOffsetZ};
    }

    public void setPosition(double pivotWorldX, double pivotWorldY, double pivotWorldZ) {
        // Rotar el offset de pivote al espacio mundo con la orientación actual
        // y restarlo para obtener el centro geométrico.
        double[] worldOffset = pivotOffsetToWorld();
        cx = pivotWorldX - worldOffset[0];
        cy = pivotWorldY - worldOffset[1];
        cz = pivotWorldZ - worldOffset[2];
    }

    public double[] getPivotWorldPosition() {
        double[] worldOffset = pivotOffsetToWorld();
        return new double[]{cx + worldOffset[0], cy + worldOffset[1], cz + worldOffset[2]};
    }

    private double[] pivotOffsetToWorld() {
        return new double[]{axisX[0] * pivotOffsetX + axisY[0] * pivotOffsetY + axisZ[0] * pivotOffsetZ, axisX[1] * pivotOffsetX + axisY[1] * pivotOffsetY + axisZ[1] * pivotOffsetZ, axisX[2] * pivotOffsetX + axisY[2] * pivotOffsetY + axisZ[2] * pivotOffsetZ};
    }

    public static OBB fromJOML(org.joml.Vector3f center, org.joml.Vector3f halfExtents, Quaternionf rotation) {
        org.joml.Matrix3f m = new org.joml.Matrix3f().rotate(rotation);
        return new OBB(center.x, center.y, center.z, halfExtents.x, halfExtents.y, halfExtents.z, new double[]{m.m00(), m.m10(), m.m20()}, new double[]{m.m01(), m.m11(), m.m21()}, new double[]{m.m02(), m.m12(), m.m22()});
    }

    public boolean intersects(OBB other) {
        double[] t = {other.cx - this.cx, other.cy - this.cy, other.cz - this.cz};

        double[][] R = new double[3][3];
        double[][] AR = new double[3][3];

        double[][] aAxes = {axisX, axisY, axisZ};
        double[][] bAxes = {other.axisX, other.axisY, other.axisZ};
        double[] aHalf = {halfX, halfY, halfZ};
        double[] bHalf = {other.halfX, other.halfY, other.halfZ};

        final double EPSILON = 1e-10;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                R[i][j] = dot(aAxes[i], bAxes[j]);
                AR[i][j] = Math.abs(R[i][j]) + EPSILON;
            }
        }

        for (int i = 0; i < 3; i++) {
            double ra = aHalf[i];
            double rb = bHalf[0] * AR[i][0] + bHalf[1] * AR[i][1] + bHalf[2] * AR[i][2];
            if (Math.abs(dot(t, aAxes[i])) > ra + rb) return false;
        }

        for (int j = 0; j < 3; j++) {
            double ra = aHalf[0] * AR[0][j] + aHalf[1] * AR[1][j] + aHalf[2] * AR[2][j];
            double rb = bHalf[j];
            if (Math.abs(dot(t, bAxes[j])) > ra + rb) return false;
        }

        // A0 × B0
        {
            double ra = aHalf[1] * AR[2][0] + aHalf[2] * AR[1][0];
            double rb = bHalf[1] * AR[0][2] + bHalf[2] * AR[0][1];
            if (Math.abs(dot(t, aAxes[2]) * R[1][0] - dot(t, aAxes[1]) * R[2][0]) > ra + rb) return false;
        }
        // A0 × B1
        {
            double ra = aHalf[1] * AR[2][1] + aHalf[2] * AR[1][1];
            double rb = bHalf[0] * AR[0][2] + bHalf[2] * AR[0][0];
            if (Math.abs(dot(t, aAxes[2]) * R[1][1] - dot(t, aAxes[1]) * R[2][1]) > ra + rb) return false;
        }
        // A0 × B2
        {
            double ra = aHalf[1] * AR[2][2] + aHalf[2] * AR[1][2];
            double rb = bHalf[0] * AR[0][1] + bHalf[1] * AR[0][0];
            if (Math.abs(dot(t, aAxes[2]) * R[1][2] - dot(t, aAxes[1]) * R[2][2]) > ra + rb) return false;
        }
        // A1 × B0
        {
            double ra = aHalf[0] * AR[2][0] + aHalf[2] * AR[0][0];
            double rb = bHalf[1] * AR[1][2] + bHalf[2] * AR[1][1];
            if (Math.abs(dot(t, aAxes[0]) * R[2][0] - dot(t, aAxes[2]) * R[0][0]) > ra + rb) return false;
        }
        // A1 × B1
        {
            double ra = aHalf[0] * AR[2][1] + aHalf[2] * AR[0][1];
            double rb = bHalf[0] * AR[1][2] + bHalf[2] * AR[1][0];
            if (Math.abs(dot(t, aAxes[0]) * R[2][1] - dot(t, aAxes[2]) * R[0][1]) > ra + rb) return false;
        }
        // A1 × B2
        {
            double ra = aHalf[0] * AR[2][2] + aHalf[2] * AR[0][2];
            double rb = bHalf[0] * AR[1][1] + bHalf[1] * AR[1][0];
            if (Math.abs(dot(t, aAxes[0]) * R[2][2] - dot(t, aAxes[2]) * R[0][2]) > ra + rb) return false;
        }
        // A2 × B0
        {
            double ra = aHalf[0] * AR[1][0] + aHalf[1] * AR[0][0];
            double rb = bHalf[1] * AR[2][2] + bHalf[2] * AR[2][1];
            if (Math.abs(dot(t, aAxes[1]) * R[0][0] - dot(t, aAxes[0]) * R[1][0]) > ra + rb) return false;
        }
        // A2 × B1
        {
            double ra = aHalf[0] * AR[1][1] + aHalf[1] * AR[0][1];
            double rb = bHalf[0] * AR[2][2] + bHalf[2] * AR[2][0];
            if (Math.abs(dot(t, aAxes[1]) * R[0][1] - dot(t, aAxes[0]) * R[1][1]) > ra + rb) return false;
        }
        // A2 × B2
        {
            double ra = aHalf[0] * AR[1][2] + aHalf[1] * AR[0][2];
            double rb = bHalf[0] * AR[2][1] + bHalf[1] * AR[2][0];
            if (Math.abs(dot(t, aAxes[1]) * R[0][2] - dot(t, aAxes[0]) * R[1][2]) > ra + rb) return false;
        }

        return true;
    }

    public boolean intersects(AABB aabb) {
        double bcx = (aabb.minX + aabb.maxX) * 0.5;
        double bcy = (aabb.minY + aabb.maxY) * 0.5;
        double bcz = (aabb.minZ + aabb.maxZ) * 0.5;
        double bhx = (aabb.maxX - aabb.minX) * 0.5;
        double bhy = (aabb.maxY - aabb.minY) * 0.5;
        double bhz = (aabb.maxZ - aabb.minZ) * 0.5;

        double[] t = {bcx - cx, bcy - cy, bcz - cz};
        double[][] aAxes = {axisX, axisY, axisZ};
        double[] aHalf = {halfX, halfY, halfZ};
        double[] bHalf = {bhx, bhy, bhz};

        final double EPSILON = 1e-10;

        // Ejes locales de la OBB
        for (int i = 0; i < 3; i++) {
            double ra = aHalf[i];
            double rb = bHalf[0] * (Math.abs(aAxes[i][0]) + EPSILON) + bHalf[1] * (Math.abs(aAxes[i][1]) + EPSILON) + bHalf[2] * (Math.abs(aAxes[i][2]) + EPSILON);
            if (Math.abs(dot(t, aAxes[i])) > ra + rb) return false;
        }

        // Ejes del mundo (X, Y, Z)
        for (int j = 0; j < 3; j++) {
            double ra = aHalf[0] * (Math.abs(aAxes[0][j]) + EPSILON) + aHalf[1] * (Math.abs(aAxes[1][j]) + EPSILON) + aHalf[2] * (Math.abs(aAxes[2][j]) + EPSILON);
            double rb = bHalf[j];
            if (Math.abs(t[j]) > ra + rb) return false;
        }

        // Productos cruzados obbAxis[i] × worldAxis[j]
        // i=0, j=0: cross=(0, axisX[2], -axisX[1])
        {
            double crossLen2 = axisX[2] * axisX[2] + axisX[1] * axisX[1];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[1] * Math.abs(axisX[2]) + aHalf[2] * Math.abs(axisX[1]);
                double rb = bHalf[1] * Math.abs(axisX[2]) + bHalf[2] * Math.abs(axisX[1]);
                if (Math.abs(-t[1] * axisX[2] + t[2] * axisX[1]) > ra + rb + EPSILON) return false;
            }
        }
        // i=0, j=1: cross=(-axisX[2], 0, axisX[0])
        {
            double crossLen2 = axisX[2] * axisX[2] + axisX[0] * axisX[0];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[1] * Math.abs(axisX[2]) + aHalf[2] * Math.abs(axisX[0]);
                double rb = bHalf[0] * Math.abs(axisX[2]) + bHalf[2] * Math.abs(axisX[0]);
                if (Math.abs(t[0] * axisX[2] - t[2] * axisX[0]) > ra + rb + EPSILON) return false;
            }
        }
        // i=0, j=2: cross=(axisX[1], -axisX[0], 0)
        {
            double crossLen2 = axisX[1] * axisX[1] + axisX[0] * axisX[0];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[1] * Math.abs(axisX[1]) + aHalf[2] * Math.abs(axisX[0]);
                double rb = bHalf[0] * Math.abs(axisX[1]) + bHalf[1] * Math.abs(axisX[0]);
                if (Math.abs(-t[0] * axisX[1] + t[1] * axisX[0]) > ra + rb + EPSILON) return false;
            }
        }
        // i=1, j=0: cross=(0, axisY[2], -axisY[1])
        {
            double crossLen2 = axisY[2] * axisY[2] + axisY[1] * axisY[1];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[0] * Math.abs(axisY[2]) + aHalf[2] * Math.abs(axisY[1]);
                double rb = bHalf[1] * Math.abs(axisY[2]) + bHalf[2] * Math.abs(axisY[1]);
                if (Math.abs(-t[1] * axisY[2] + t[2] * axisY[1]) > ra + rb + EPSILON) return false;
            }
        }
        // i=1, j=1: cross=(-axisY[2], 0, axisY[0])
        {
            double crossLen2 = axisY[2] * axisY[2] + axisY[0] * axisY[0];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[0] * Math.abs(axisY[2]) + aHalf[2] * Math.abs(axisY[0]);
                double rb = bHalf[0] * Math.abs(axisY[2]) + bHalf[2] * Math.abs(axisY[0]);
                if (Math.abs(t[0] * axisY[2] - t[2] * axisY[0]) > ra + rb + EPSILON) return false;
            }
        }
        // i=1, j=2: cross=(axisY[1], -axisY[0], 0)
        {
            double crossLen2 = axisY[1] * axisY[1] + axisY[0] * axisY[0];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[0] * Math.abs(axisY[1]) + aHalf[2] * Math.abs(axisY[0]);
                double rb = bHalf[0] * Math.abs(axisY[1]) + bHalf[1] * Math.abs(axisY[0]);
                if (Math.abs(-t[0] * axisY[1] + t[1] * axisY[0]) > ra + rb + EPSILON) return false;
            }
        }
        // i=2, j=0: cross=(0, axisZ[2], -axisZ[1])
        {
            double crossLen2 = axisZ[2] * axisZ[2] + axisZ[1] * axisZ[1];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[0] * Math.abs(axisZ[2]) + aHalf[1] * Math.abs(axisZ[1]);
                double rb = bHalf[1] * Math.abs(axisZ[2]) + bHalf[2] * Math.abs(axisZ[1]);
                if (Math.abs(-t[1] * axisZ[2] + t[2] * axisZ[1]) > ra + rb + EPSILON) return false;
            }
        }
        // i=2, j=1: cross=(-axisZ[2], 0, axisZ[0])
        {
            double crossLen2 = axisZ[2] * axisZ[2] + axisZ[0] * axisZ[0];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[0] * Math.abs(axisZ[2]) + aHalf[1] * Math.abs(axisZ[0]);
                double rb = bHalf[0] * Math.abs(axisZ[2]) + bHalf[2] * Math.abs(axisZ[0]);
                if (Math.abs(t[0] * axisZ[2] - t[2] * axisZ[0]) > ra + rb + EPSILON) return false;
            }
        }
        // i=2, j=2: cross=(axisZ[1], -axisZ[0], 0)
        {
            double crossLen2 = axisZ[1] * axisZ[1] + axisZ[0] * axisZ[0];
            if (crossLen2 > EPSILON) {
                double ra = aHalf[0] * Math.abs(axisZ[1]) + aHalf[1] * Math.abs(axisZ[0]);
                double rb = bHalf[0] * Math.abs(axisZ[1]) + bHalf[1] * Math.abs(axisZ[0]);
                if (Math.abs(-t[0] * axisZ[1] + t[1] * axisZ[0]) > ra + rb + EPSILON) return false;
            }
        }

        return true;
    }

    public double raycast(double[] rayOrigin, double[] rayDirection) {
        double tMin = -Double.MAX_VALUE;
        double tMax = Double.MAX_VALUE;

        double[][] axes = {axisX, axisY, axisZ};
        double[] halves = {halfX, halfY, halfZ};

        double[] d = {cx - rayOrigin[0], cy - rayOrigin[1], cz - rayOrigin[2]};

        for (int i = 0; i < 3; i++) {
            double e = dot(axes[i], d);
            double f = dot(axes[i], rayDirection);
            if (Math.abs(f) > 1e-10) {
                double t1 = (e + halves[i]) / f;
                double t2 = (e - halves[i]) / f;
                if (t1 > t2) {
                    double tmp = t1;
                    t1 = t2;
                    t2 = tmp;
                }
                tMin = Math.max(tMin, t1);
                tMax = Math.min(tMax, t2);
                if (tMin > tMax) return -1;
            } else if (-e - halves[i] > 0 || -e + halves[i] < 0) {
                return -1;
            }
        }
        return tMin >= 0 ? tMin : (tMax >= 0 ? tMax : -1);
    }

    public double[] closestPoint(double[] p) {
        double[] d = {p[0] - cx, p[1] - cy, p[2] - cz};
        double[] result = {cx, cy, cz};
        double[][] axes = {axisX, axisY, axisZ};
        double[] halves = {halfX, halfY, halfZ};
        for (int i = 0; i < 3; i++) {
            double dist = Math.max(-halves[i], Math.min(dot(d, axes[i]), halves[i]));
            result[0] += dist * axes[i][0];
            result[1] += dist * axes[i][1];
            result[2] += dist * axes[i][2];
        }
        return result;
    }

    public double distanceSq(double[] p) {
        double[] closest = closestPoint(p);
        double dx = p[0] - closest[0], dy = p[1] - closest[1], dz = p[2] - closest[2];
        return dx * dx + dy * dy + dz * dz;
    }

    public void rotate(Quaternionf q) {
        float len = (float) Math.sqrt(q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        float qx = q.x / len, qy = q.y / len, qz = q.z / len, qw = q.w / len;

        float x2 = qx + qx, y2 = qy + qy, z2 = qz + qz;
        float xx = qx * x2, xy = qx * y2, xz = qx * z2;
        float yy = qy * y2, yz = qy * z2, zz = qz * z2;
        float wx = qw * x2, wy = qw * y2, wz = qw * z2;

        axisX = new double[]{1 - (yy + zz), xy + wz, xz - wy};
        axisY = new double[]{xy - wz, 1 - (xx + zz), yz + wx};
        axisZ = new double[]{xz + wy, yz - wx, 1 - (xx + yy)};
    }

    public void rotateAndSetPosition(Quaternionf q, double pivotWorldX, double pivotWorldY, double pivotWorldZ) {
        rotate(q);
        setPosition(pivotWorldX, pivotWorldY, pivotWorldZ);
    }

    public AABB toAABB() {
        double ex = halfX * Math.abs(axisX[0]) + halfY * Math.abs(axisY[0]) + halfZ * Math.abs(axisZ[0]);
        double ey = halfX * Math.abs(axisX[1]) + halfY * Math.abs(axisY[1]) + halfZ * Math.abs(axisZ[1]);
        double ez = halfX * Math.abs(axisX[2]) + halfY * Math.abs(axisY[2]) + halfZ * Math.abs(axisZ[2]);
        return new AABB(cx - ex, cy - ey, cz - ez, cx + ex, cy + ey, cz + ez);
    }

    private static double dot(double[] a, double[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    private static double[] normalize(double[] v) {
        double len = Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
        if (len < 1e-12) return new double[]{0, 0, 0};
        return new double[]{v[0] / len, v[1] / len, v[2] / len};
    }

    @Override
    public String toString() {
        double[] pivot = getPivotWorldPosition();
        return String.format("OBB[center=(%.2f,%.2f,%.2f) half=(%.2f,%.2f,%.2f) pivot=(%.2f,%.2f,%.2f) axisX=(%.2f,%.2f,%.2f)]", cx, cy, cz, halfX, halfY, halfZ, pivot[0], pivot[1], pivot[2], axisX[0], axisX[1], axisX[2]);
    }
}
