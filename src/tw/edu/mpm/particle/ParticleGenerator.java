package tw.edu.mpm.particle;

import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * ParticleGenerator.java
 *
 *
 * Material Point 產生器
 *
 *
 * 功能：
 *
 *     1. 建立規則排列Particle
 *     2. 建立土體模型
 *     3. 設定Material Property
 *
 *
 * ============================================================
 */

public class ParticleGenerator {

	/*
	 * ========================================================
	 *
	 * Constructor
	 *
	 * ========================================================
	 */

	private ParticleGenerator() {

	}

	/*
	 * ========================================================
	 *
	 * 建立長方體Particle Block
	 *
	 *
	 * length方向：
	 *
	 * nx
	 * ny
	 * nz
	 *
	 *
	 * ========================================================
	 */

	public static ParticleSet createBlock(Vector3 origin, int nx, int ny, int nz, double spacing, double density) {

		ParticleSet particles =

				new ParticleSet(

						nx * ny * nz

				);

		/*
		 *
		 * 單一Particle體積
		 *
		 */

		double volume =	spacing * spacing * spacing;

		/*
		 *
		 * Particle質量
		 *
		 *
		 * m=rho V
		 *
		 */

		double mass = density * volume;

		int id = 0;

		for (int k = 0; k < nz; k++) {

			for (int j = 0; j < ny; j++) {

				for (int i = 0; i < nx; i++) {

					Vector3 position =

							new Vector3(

									origin.x + i * spacing,

									origin.y + j * spacing,

									origin.z + k * spacing

							);

					MaterialPoint particle =

							new MaterialPoint(

									id,

									position

							);

					particle.setMass(mass);

					particle.setVolume(volume);

					particle.setDensity(density);

					particles.add(particle);

					id++;

				}

			}

		}

		return particles;

	}

	/*
	 * ========================================================
	 *
	 * 建立2D薄層模型
	 *
	 *
	 * 適合：
	 *
	 *     平面應變分析
	 *
	 *
	 * nz=1
	 *
	 * ========================================================
	 */

	public static ParticleSet createPlaneBlock(Vector3 origin, int nx, int ny, double spacing, double thickness, double density) {

		ParticleSet particles =

				createBlock(

						origin,

						nx,

						ny,

						1,

						spacing,

						density

				);

		double volume =	spacing * spacing * thickness;

		double mass = density * volume;

		for (MaterialPoint p : particles) {

			p.setVolume(volume);

			p.setMass(mass);

		}

		return particles;

	}

	/*
	 * ========================================================
	 *
	 * 建立單一Particle
	 *
	 * ========================================================
	 */

	public static MaterialPoint createParticle(int id, Vector3 position, double mass, double volume, double density) {

		MaterialPoint p =

				new MaterialPoint(

						id,

						position

				);

		p.setMass(mass);

		p.setVolume(volume);

		p.setDensity(density);

		return p;

	}

	/*
	 * ========================================================
	 *
	 * 建立球形區域Particle
	 *
	 *
	 * 用於：
	 *
	 *     滑動塊
	 *     堰塞湖崩塌
	 *
	 * ========================================================
	 */

	public static ParticleSet createSphere(Vector3 center, double radius, double spacing, double density) {

		int n =	(int) (radius * 2 / spacing) + 1;

		ParticleSet particles =	new ParticleSet();

		double volume =	spacing * spacing * spacing;

		double mass = density * volume;

		int id = 0;

		for (int k = 0; k < n; k++) {

			for (int j = 0; j < n; j++) {

				for (int i = 0; i < n; i++) {

					Vector3 pos =

							new Vector3(

									center.x - radius + i * spacing,

									center.y - radius + j * spacing,

									center.z - radius + k * spacing

							);

					if (pos.subtract(center).magnitude() <=	radius) {

						MaterialPoint p =

								new MaterialPoint(

										id++,

										pos

								);

						p.setVolume(volume);

						p.setMass(mass);

						p.setDensity(density);

						particles.add(p);

					}

				}

			}

		}

		return particles;

	}

}