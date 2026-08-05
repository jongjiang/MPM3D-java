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
 * DamBreakExample.java
 *
 *
 * 堰塞湖潰決案例
 *
 *
 * Application:
 *
 *     Landslide dam breach
 *
 *     Debris flow
 *
 *
 * ============================================================
 */

public class DamBreakExample {

	public static void main(String[] args) {

		System.out.println("MPM Dam Break Example");

		/*
		 * ====================================================
		 *
		 * 1. Background Grid
		 * 模擬河谷區域
		 *
		 * ====================================================
		 */

		Grid grid =	new Grid(100, 40, 20, 0.25);

		/*
		 * ====================================================
		 *
		 * 2. 建立堰塞湖土石體
		 *
		 *        Dam
		 *
		 *        ███████
		 *        ███████
		 *        ███████
		 *
		 * ====================================================
		 */

		ParticleSet damSoil = ParticleGenerator.createBlock(new Vector3(8.0, 0.25, 2.0), 20, 15, 10, 0.2, 2000.0);

		/*
		 * ====================================================
		 *
		 * 3. 建立湖水材料點
		 *
		 *        Water
		 *
		 *     █████████
		 *     █████████
		 *
		 * ====================================================
		 */

		ParticleSet water =	ParticleGenerator.createBlock(new Vector3(1.0, 0.25, 2.0), 30, 15, 10, 0.2,	1000.0);

		/*
		 * ====================================================
		 *
		 * 4. 合併 Particle Set
		 *
		 * ====================================================
		 */

		ParticleSet particles =	new ParticleSet();
		particles.addAll(damSoil);
		particles.addAll(water);

		/*
		 * ====================================================
		 *
		 * 5. Solver
		 *
		 * ====================================================
		 */

		MPMSolver solver = new MPMSolver(grid, particles);

		/*
		 * ====================================================
		 *
		 * 6. Material Property
		 *
		 * 土石材料
		 *
		 * ====================================================
		 */

		StressUpdate material =	new StressUpdate(1.0e7,	0.30);
		solver.setStressUpdate(material);

		/*
		 * ====================================================
		 *
		 * 7. Time control
		 *
		 * ====================================================
		 */

		solver.setTimeStep(0.0005);
		solver.setEndTime(8.0);

		/*
		 * ====================================================
		 *
		 * 8. Run simulation
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

		System.out.println("Dam Break Finished");
		System.out.println("Particle number = "	+ particles.size());

	}

}