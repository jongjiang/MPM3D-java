package tw.edu.mpm.solver;

import tw.edu.mpm.mesh.Grid;
import tw.edu.mpm.mesh.Cell;
import tw.edu.mpm.mesh.GridNode;

import tw.edu.mpm.particle.MaterialPoint;
import tw.edu.mpm.particle.ParticleSet;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * G2PTransfer.java
 *
 *
 * Grid To Particle Transfer
 *
 *
 * Grid → Particle
 *
 *
 * 功能：
 *
 *     1. 更新Particle velocity
 *     2. 更新Particle acceleration
 *     3. 更新Particle position
 *
 *
 * ============================================================
 */

public class G2PTransfer {

	/**
	 *
	 * Grid To Particle
	 *
	 *
	 * @param particles Material Points
	 * @param grid Background Grid
	 * @param dt timestep
	 *
	 */

	public static void transfer(ParticleSet particles, Grid grid, double dt) {

		/*
		 *
		 * 逐Particle處理
		 *
		 */

		for (MaterialPoint particle : particles) {

			/*
			 *
			 * 找Particle所在Cell
			 *
			 */

			Cell cell =

					grid.findCell(

							particle.getPosition()

					);

			/*
			 *
			 * 超出Grid
			 *
			 */

			if (cell == null) {

				continue;

			}

			/*
			 *
			 * Shape Function
			 *
			 */

			double[] N =

					cell.shapeFunction(

							particle.getPosition()

					);

			GridNode[] nodes =

					cell.getNodes();

			Vector3 velocity =

					new Vector3();

			Vector3 acceleration =

					new Vector3();

			/*
			 *
			 * Grid → Particle
			 *
			 *
			 * vp=sum(Ni vi)
			 *
			 *
			 */

			for (int i = 0; i < 8; i++) {

				GridNode node =

						nodes[i];

				double weight = N[i];

				velocity.addInPlace(

						node.getVelocity().multiply(weight)

				);

				/*
				 *
				 * acceleration
				 *
				 *
				 * ai=fi/mi
				 *
				 *
				 */

				if (node.getMass() > 1e-12) {

					//Vector3 force =	node.getInternalForce().add(node.getExternalForce());
					Vector3 force =	node.getInternalForce();
					force.addInPlace(node.getExternalForce());

					Vector3 ai = force.divide(node.getMass());

					acceleration.addInPlace(ai.multiply(weight));

				}

			}

			/*
			 *
			 * 更新Particle
			 *
			 */

			particle.setVelocity(

					velocity

			);

			/*
			 *
			 * 設定Acceleration
			 *
			 */

			particle.getAcceleration().addInPlace(

					acceleration

			);

			/*
			 *
			 * 更新位置
			 *
			 */

			particle.updatePosition(

					dt

			);

		}

	}

}