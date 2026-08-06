package tw.edu.mpm.example;

import tw.edu.mpm.math.Vector3;

import tw.edu.mpm.mesh.Grid;

import tw.edu.mpm.particle.ParticleGenerator;
import tw.edu.mpm.particle.ParticleSet;

import tw.edu.mpm.solver.MPMSolver;
import tw.edu.mpm.solver.StressUpdate;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * SlopeFailureExample.java
 * 邊坡滑動分析案例
 *
 * Purpose:
 *
 *     1. Gravity loading
 *     2. Large deformation
 *     3. Soil movement
 *
 * ============================================================
 */
public class SlopeFailureExample {

	public static void main(String[] args) {

		System.out.println("MPM Slope Failure Example");

		/*
		 * ====================================================
		 *
		 * 1. Background Grid
		 *
		 * ====================================================
		 */

		Grid grid =	new Grid(60, 40, 10, 0.25);

		/*
		 * ====================================================
		 *
		 * 2. 建立坡體 Material Points
		 *
		 *              Soil
		 *
		 *                 ███
		 *              ██████
		 *           █████████
		 *        ████████████
		 *
		 * ====================================================
		 */

		ParticleSet slope =	ParticleGenerator.createBlock(new Vector3(0.5, 0.25, 0.25),	40,	20,	5, 0.2,	1800.0);

		/*
		 * ====================================================
		 *
		 * 3. Solver
		 *
		 * ====================================================
		 */

		MPMSolver solver = new MPMSolver(grid, slope);

		/*
		 * ====================================================
		 *
		 * 4. Soil Elastic Parameter
		 *
		 * ====================================================
		 */

		StressUpdate soil =	new StressUpdate(2.0e7,	0.30);
		solver.setStressUpdate(soil);

		/*
		 * ====================================================
		 *
		 * 5. Time step
		 *
		 * ====================================================
		 */

		solver.setTimeStep(0.0005);
		solver.setEndTime(5.0);

		/*
		 * ====================================================
		 *
		 * 6. Run
		 *
		 * ====================================================
		 */

		solver.run();

		/*
		 * ====================================================
		 *
		 * Output
		 *
		 * ====================================================
		 */

		System.out.println("Final slope particle");
		System.out.println(slope.get(0).getPosition());
		System.out.println("Slope simulation finished");

	}

}