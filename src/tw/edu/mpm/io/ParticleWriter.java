package tw.edu.mpm.io;

import java.io.FileWriter;
import java.io.IOException;

import tw.edu.mpm.math.Vector3;
import tw.edu.mpm.particle.MaterialPoint;
import tw.edu.mpm.particle.ParticleSet;

/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * ParticleWriter.java
 *
 *
 * Material Point VTK Writer
 *
 *
 * 輸出:
 *
 *     Particle position
 *
 *     Velocity
 *
 *     Mass
 *
 *     Density
 *
 *     ID
 *
 *
 * 格式:
 *
 *     VTK PolyData (.vtp)
 *
 *
 * ============================================================
 */

public class ParticleWriter {

	/**
	 * ========================================================
	 *
	 * Write particles
	 *
	 * ========================================================
	 */

	public static void write(

			ParticleSet particles,

			String filename

	) throws IOException {

		FileWriter out =

				new FileWriter(filename);

		int n =

				particles.size();

		/*
		 * ====================================================
		 *
		 * VTK Header
		 *
		 * ====================================================
		 */

		out.write(

				"<?xml version=\"1.0\"?>\n"

		);

		out.write(

				"<VTKFile type=\"PolyData\" version=\"0.1\">\n"

		);

		out.write(

				"<PolyData>\n"

		);

		out.write(

				"<Piece NumberOfPoints=\""

						+

						n

						+

						"\" NumberOfVerts=\""

						+

						n

						+

						"\">\n"

		);

		/*
		 * ====================================================
		 *
		 * Points
		 *
		 * Particle position
		 *
		 * ====================================================
		 */

		out.write(

				"<Points>\n"

		);

		out.write(

				"<DataArray type=\"Float64\" NumberOfComponents=\"3\" format=\"ascii\">\n"

		);

		for (MaterialPoint p : particles)

		{

			Vector3 x =

					p.getPosition();

			out.write(

					x.x + " "

							+

							x.y + " "

							+

							x.z

							+

							"\n"

			);

		}

		out.write(

				"</DataArray>\n"

		);

		out.write(

				"</Points>\n"

		);

		/*
		 * ====================================================
		 *
		 * Point Data
		 *
		 * ====================================================
		 */

		out.write(

				"<PointData>\n"

		);

		/*
		 * ----------------------------------------------------
		 *
		 * Velocity
		 *
		 * ----------------------------------------------------
		 */

		out.write(

				"<DataArray type=\"Float64\" Name=\"velocity\" NumberOfComponents=\"3\" format=\"ascii\">\n"

		);

		for (MaterialPoint p : particles)

		{

			Vector3 v =

					p.getVelocity();

			out.write(

					v.x + " "

							+

							v.y + " "

							+

							v.z

							+

							"\n"

			);

		}

		out.write(

				"</DataArray>\n"

		);

		/*
		 * ----------------------------------------------------
		 *
		 * Mass
		 *
		 * ----------------------------------------------------
		 */

		out.write(

				"<DataArray type=\"Float64\" Name=\"mass\" NumberOfComponents=\"1\" format=\"ascii\">\n"

		);

		for (MaterialPoint p : particles)

		{

			out.write(

					p.getMass()

							+

							"\n"

			);

		}

		out.write(

				"</DataArray>\n"

		);

		/*
		 * ----------------------------------------------------
		 *
		 * Density
		 *
		 * ----------------------------------------------------
		 */

		out.write(

				"<DataArray type=\"Float64\" Name=\"density\" NumberOfComponents=\"1\" format=\"ascii\">\n"

		);

		for (MaterialPoint p : particles)

		{

			out.write(

					p.getDensity()

							+

							"\n"

			);

		}

		out.write(

				"</DataArray>\n"

		);

		/*
		 * ----------------------------------------------------
		 *
		 * Particle ID
		 *
		 * ----------------------------------------------------
		 */

		out.write(

				"<DataArray type=\"Int32\" Name=\"id\" NumberOfComponents=\"1\" format=\"ascii\">\n"

		);

		int id = 0;

		for (MaterialPoint p : particles)

		{

			out.write(

					id

							+

							"\n"

			);

			id++;

		}

		out.write(

				"</DataArray>\n"

		);

		out.write(

				"</PointData>\n"

		);

		/*
		 * ====================================================
		 *
		 * Vertices
		 *
		 * ====================================================
		 */

		out.write(

				"<Verts>\n"

		);

		/*
		 *
		 * connectivity
		 *
		 */

		out.write(

				"<DataArray type=\"Int32\" Name=\"connectivity\" format=\"ascii\">\n"

		);

		for (int i = 0; i < n; i++)

		{

			out.write(

					i + "\n"

			);

		}

		out.write(

				"</DataArray>\n"

		);

		/*
		 *
		 * offsets
		 *
		 */

		out.write(

				"<DataArray type=\"Int32\" Name=\"offsets\" format=\"ascii\">\n"

		);

		for (int i = 1; i <= n; i++)

		{

			out.write(

					i + "\n"

			);

		}

		out.write(

				"</DataArray>\n"

		);

		out.write(

				"</Verts>\n"

		);

		/*
		 * ====================================================
		 *
		 * Close VTK
		 *
		 * ====================================================
		 */

		out.write(

				"</Piece>\n"

		);

		out.write(

				"</PolyData>\n"

		);

		out.write(

				"</VTKFile>\n"

		);

		out.close();

	}

}