package tw.edu.mpm.solver;

import tw.edu.mpm.mesh.Grid;
import tw.edu.mpm.mesh.GridNode;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * GridSolver.java
 *
 *
 * Background Grid Momentum Solver
 *
 *
 * 功能：
 *
 *     1. Grid momentum update
 *     2. Gravity force
 *     3. Velocity update
 *     4. Boundary condition
 *
 * ============================================================
 */

public class GridSolver {

	/*
	 * ========================================================
	 *
	 * Gravity
	 *
	 * ========================================================
	 */

	private Vector3 gravity;

	/*
	 * ========================================================
	 *
	 * Constructor
	 *
	 * ========================================================
	 */

	public GridSolver() {

		/*
		 *
		 * 地球重力
		 *
		 */

		gravity = new Vector3(0.0, -9.81, 0.0);

	}

	/**
	 *
	 * 自訂Gravity
	 *
	 */

	public GridSolver(Vector3 gravity) {

		this.gravity = gravity;

	}

	/*
	 * ========================================================
	 *
	 * Solve Grid
	 *
	 * p(n+1) = p(n)+fΔt
	 *
	 * ========================================================
	 */

	public void solve(Grid grid, double dt) {

		for (GridNode node : grid.getNodes()) {

			/*
			 * 無質量Node不計算
			 */
			if (node.getMass() < 1e-12) {
				continue;
			}

			/*
			 * Gravity: f = m g
			 */
			Vector3 gravityForce = gravity.multiply(node.getMass());
			node.addExternalForce(gravityForce);

			/*
			 * Momentum update
			 */
			node.updateMomentum(dt);

			/*
			 * velocity
			 */
			node.updateVelocity();

		}

		/*
		 *
		 * Boundary Constraint
		 *
		 */
		grid.applyBoundary();

	}

	/*
	 * ========================================================
	 *
	 * 計算Grid kinetic energy
	 *
	 *
	 * E=1/2 mv²
	 *
	 * ========================================================
	 */

	public double kineticEnergy(Grid grid) {

		double energy = 0.0;

		for (GridNode node : grid.getNodes()) {
			double m = node.getMass();
			double v = node.getVelocity().magnitude();
			energy += 0.5 * m * v * v;
		}

		return energy;

	}

	/*
	 * ========================================================
	 *
	 * Setter
	 *
	 * ========================================================
	 */

	public void setGravity(Vector3 gravity) {
		this.gravity = gravity;
	}

	public Vector3 getGravity() {
		return gravity;
	}

}