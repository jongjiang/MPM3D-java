package tw.edu.mpm.shape;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * LinearShapeFunction.java
 *
 *
 * Linear hat function
 *
 *
 * MPM original formulation
 *
 * ============================================================
 */

public class LinearShapeFunction implements ShapeFunction {

	private double dx;

	public LinearShapeFunction(double cellSize) {

		this.dx = cellSize;

	}

	/**
	 *
	 * 1D linear weight
	 *
	 */
	private double weight1D(double x) {

		double r = Math.abs(x) / dx;

		if (r >= 1.0)
			return 0.0;

		return 1.0 - r;

	}

	@Override
	public double value(
			Vector3 p,
			Vector3 n
	) {

		double wx = weight1D(p.x - n.x);

		double wy = weight1D(p.y - n.y);

		double wz = weight1D(p.z - n.z);

		return wx * wy * wz;

	}

	@Override
	public Vector3 gradient(
			Vector3 p,
			Vector3 n
	) {

		double gx = gradient1D(p.x - n.x);

		double gy = gradient1D(p.y - n.y);

		double gz = gradient1D(p.z - n.z);

		return new Vector3(gx, gy, gz);

	}

	private double gradient1D(double x) {

		if (Math.abs(x) >= dx)
			return 0.0;

		if (x > 0)
			return -1.0 / dx;
		else
			return 1.0 / dx;

	}

}