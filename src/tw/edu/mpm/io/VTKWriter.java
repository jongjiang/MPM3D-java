package tw.edu.mpm.io;

import java.io.FileWriter;
import java.io.IOException;

import tw.edu.mpm.particle.MaterialPoint;
import tw.edu.mpm.particle.ParticleSet;
import tw.edu.mpm.math.Vector3;

/**
 * ============================================================
 *
 * VTKWriter.java
 *
 *
 * 輸出 Material Point 到 VTK
 *
 *
 * 支援：
 *
 *     ParaView
 *
 *
 * ============================================================
 */

public class VTKWriter {

	/**
	 *
	 * Write particle vtk
	 *
	 */

	public static void writeParticles(

			ParticleSet particles,

			String filename

	) throws IOException {

		FileWriter out = new FileWriter(filename);

		int n = particles.size();

		out.write("<?xml version=\"1.0\"?>\n");

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
		 *
		 * Point coordinates
		 *
		 */

		out.write(

				"<Points>\n"

		);

		out.write(

				"<DataArray type=\"Float32\" NumberOfComponents=\"3\" format=\"ascii\">\n"

		);

		for (MaterialPoint p : particles)

		{

			Vector3 x = p.getPosition();

			out.write(

					x.x + " " + x.y + " " + x.z + "\n"

			);

		}

		out.write(

				"</DataArray>\n"

		);

		out.write(

				"</Points>\n"

		);

		/*
		 *
		 * Vertices
		 *
		 */

		out.write(

				"<Verts>\n"

		);

		out.write(

				"<DataArray type=\"Int32\" Name=\"connectivity\" format=\"ascii\">\n"

		);

		for (int i = 0; i < n; i++)

		{

			out.write(i + "\n");

		}

		out.write(

				"</DataArray>\n"

		);

		out.write(

				"<DataArray type=\"Int32\" Name=\"offsets\" format=\"ascii\">\n"

		);

		for (int i = 1; i <= n; i++)

		{

			out.write(i + "\n");

		}

		out.write(

				"</DataArray>\n"

		);

		out.write(

				"</Verts>\n"

		);

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