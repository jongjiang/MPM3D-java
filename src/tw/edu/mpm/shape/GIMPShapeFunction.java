package tw.edu.mpm.shape;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * GIMPShapeFunction.java
 *
 *
 * Generalized Interpolation Material Point Method
 *
 *
 * ============================================================
 */

public class GIMPShapeFunction implements ShapeFunction {

	private double dx;

	private double particleSize;

	public GIMPShapeFunction(

			double cellSize,

			double particleSize

	) {

		this.dx = cellSize;

		this.particleSize = particleSize;

	}

	/**
	 *
	 * GIMP 1D kernel
	 *
	 */

	private double gimp1D(

			double x

	) {

		double a = Math.abs(x);

		double lp = particleSize;

		if (a <= lp) {
			return 1.0 - a / (dx + lp);
		}

		if (a <= dx + lp) {
			return (dx + lp - a) / (dx + lp);
		}

		return 0.0;

	}

	@Override

	public double value(

			Vector3 p,

			Vector3 n

	) {

		return

		gimp1D(p.x - n.x)

				*

				gimp1D(p.y - n.y)

				*

				gimp1D(p.z - n.z);

	}

	@Override

	public Vector3 gradient(

			Vector3 p,

			Vector3 n

	) {

		/*
		 *
		 * 簡化版gradient
		 *
		 *
		 * 後續可改解析積分形式
		 *
		 */

		double eps = 1e-6;

		double gx =

				(value(

						new Vector3(

								p.x + eps,

								p.y,

								p.z

						),

						n

				)

						-

						value(

								new Vector3(

										p.x - eps,

										p.y,

										p.z

								),

								n

						)

				)

						/ (2 * eps);

		double gy =

				(value(

						new Vector3(

								p.x,

								p.y + eps,

								p.z

						),

						n

				)

						-

						value(

								new Vector3(

										p.x,

										p.y - eps,

										p.z

								),

								n

						)

				)

						/ (2 * eps);

		double gz =

				(value(

						new Vector3(

								p.x,

								p.y,

								p.z + eps

						),

						n

				)

						-

						value(

								new Vector3(

										p.x,

										p.y,

										p.z - eps

								),

								n

						)

				)

						/ (2 * eps);

		return new Vector3(

				gx,

				gy,

				gz

		);

	}

}