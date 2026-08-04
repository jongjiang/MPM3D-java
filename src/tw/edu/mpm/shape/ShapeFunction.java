package tw.edu.mpm.shape;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * ShapeFunction.java
 *
 *
 * MPM Shape Function Interface
 *
 *
 * 定義：
 *
 *     S(xp-xi)
 *
 *     grad S
 *
 *
 * ============================================================
 */

public interface ShapeFunction {

	/**
	 *
	 * 計算Particle到Grid Node權重
	 *
	 *
	 * @param particlePosition
	 * @param nodePosition
	 *
	 * @return weight
	 *
	 */
	double value(

			Vector3 particlePosition,

			Vector3 nodePosition

	);

	/**
	 *
	 * Shape function gradient
	 *
	 *
	 * ∇S
	 *
	 */
	Vector3 gradient(

			Vector3 particlePosition,

			Vector3 nodePosition

	);

}