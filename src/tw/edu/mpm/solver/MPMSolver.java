package tw.edu.mpm.solver;

import java.io.IOException;

import tw.edu.mpm.io.VTKWriter;
import tw.edu.mpm.mesh.Grid;
import tw.edu.mpm.particle.ParticleSet;
import tw.edu.mpm.util.Constants;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * MPMSolver.java
 *
 *
 * Material Point Method Main Solver
 *
 *
 * 功能：
 *
 *     1. 控制MPM時間積分
 *     2. 串接P2G/Grid/G2P
 *     3. 管理Simulation Time
 *
 *
 * ============================================================
 */

public class MPMSolver {

	/*
	 * ========================================================
	 *
	 * Model Data
	 *
	 * ========================================================
	 */

	private Grid grid;

	private ParticleSet particles;

	/*
	 * ========================================================
	 *
	 * Solver Modules
	 *
	 * ========================================================
	 */

	private GridSolver gridSolver;

	private StressUpdate stressUpdate;

	/*
	 * ========================================================
	 *
	 * Time Control
	 *
	 * ========================================================
	 */

	private double time;

	private double dt;

	private double endTime;

	/*
	 * ========================================================
	 *
	 * Constructor
	 *
	 * ========================================================
	 */

	public MPMSolver(Grid grid, ParticleSet particles) {

		this.grid = grid;

		this.particles = particles;

		this.gridSolver =

				new GridSolver();

		this.stressUpdate =

				new StressUpdate(

						Constants.YOUNG_MODULUS,

						Constants.POISSON_RATIO

				);

		this.dt = Constants.TIME_STEP;

		this.time = 0.0;

		this.endTime = 1.0;

	}

	/**
	 *
	 * 設定材料模型
	 *
	 */

	public void setStressUpdate(StressUpdate model) {

		this.stressUpdate = model;

	}

	/**
	 *
	 * 設定時間步
	 *
	 */

	public void setTimeStep(double dt) {

		this.dt = dt;

	}

	/**
	 *
	 * 設定總模擬時間
	 *
	 */

	public void setEndTime(double endTime) {

		this.endTime = endTime;

	}

	/*
	 * ========================================================
	 *
	 * Single Time Step
	 *
	 * ========================================================
	 */

	public void step() {

		/*
		 *
		 * 1.
		 *
		 * Stress Update
		 *
		 */

		stressUpdate.update(

				particles,

				grid,

				dt

		);

		/*
		 *
		 * 2.
		 *
		 * Particle → Grid
		 *
		 */

		P2GTransfer.transfer(

				particles,

				grid

		);

		/*
		 *
		 * 3.
		 *
		 * Grid Momentum Solve
		 *
		 */

		gridSolver.solve(

				grid,

				dt

		);

		/*
		 *
		 * 4.
		 *
		 * Grid → Particle
		 *
		 */

		G2PTransfer.transfer(

				particles,

				grid,

				dt

		);

		/*
		 *
		 * Simulation Time
		 *
		 */

		time += dt;

	}

	/*
	 * ========================================================
	 *
	 * Run Simulation
	 *
	 * ========================================================
	 */

	public void run() {

		System.out.println(

				"===== MPM Simulation Start ====="

		);

		int step = 0;

		while (time < endTime) {

			step();

			step++;

			if (step % 100 == 0) {

				printStatus(step);
				
			    try {
					VTKWriter.writeParticles(
							        particles,
							        "output/particle_"
							        +
							        step
							        +
							        ".vtp"
							    );
				} catch (IOException e) {
					e.printStackTrace();
				}
				

			}

		}
		
		System.out.println("===== MPM Simulation Finished =====");

	}

	/*
	 * ========================================================
	 *
	 * Status Output
	 *
	 * ========================================================
	 */

	private void printStatus(int step) {

		System.out.println(

				"Step=" + step + " Time=" + time + " Particle=" + particles.size() + " Energy=" + gridSolver.kineticEnergy(grid)

		);

	}

	/*
	 * ========================================================
	 *
	 * Getter
	 *
	 * ========================================================
	 */

	public double getTime() {

		return time;

	}

	public double getDt() {

		return dt;

	}

	public Grid getGrid() {

		return grid;

	}

	public ParticleSet getParticles() {

		return particles;

	}

}