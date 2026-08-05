package tw.edu.mpm.io;


import java.io.FileWriter;
import java.io.IOException;


import tw.edu.mpm.mesh.Grid;
import tw.edu.mpm.mesh.GridNode;
import tw.edu.mpm.math.Vector3;



/**
 * ============================================================
 *
 * GridWriter.java
 *
 *
 * VTK Grid Output
 *
 *
 * 輸出 MPM Background Grid
 *
 *
 * 支援：
 *
 *     Node position
 *
 *     Velocity field
 *
 *     Mass field
 *
 *     Force field
 *
 *
 * 格式：
 *
 *     VTK Unstructured Grid
 *
 *
 * ============================================================
 */


public class GridWriter {





    /**
     *
     * Write Grid to VTK
     *
     */


    public static void write(

            Grid grid,

            String filename

    )
    throws IOException
    {



        FileWriter out =

                new FileWriter(filename);





        int nodeCount =

                grid.getNodeCount();





        int cellCount =

                grid.getCellCount();








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

        "<VTKFile type=\"UnstructuredGrid\" version=\"0.1\">\n"

        );


        out.write(

        "<UnstructuredGrid>\n"

        );




        out.write(

        "<Piece NumberOfPoints=\""

        +

        nodeCount

        +

        "\" NumberOfCells=\""

        +

        cellCount

        +

        "\">\n"

        );









        /*
         * ====================================================
         *
         * Points
         *
         * Grid Node coordinates
         *
         * ====================================================
         */


        out.write(

        "<Points>\n"

        );



        out.write(

        "<DataArray type=\"Float64\" NumberOfComponents=\"3\" format=\"ascii\">\n"

        );




        for(int i=0;i<nodeCount;i++)

        {


            GridNode node =

                    grid.getNode(i);



            Vector3 x =

                    node.getPosition();




            out.write(

                    x.x+" "

                    +

                    x.y+" "

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
         * Velocity / Mass / Force
         *
         * ====================================================
         */


        out.write(

        "<PointData>\n"

        );








        /*
         *
         * Velocity
         *
         */


        out.write(

        "<DataArray type=\"Float64\" Name=\"velocity\" NumberOfComponents=\"3\" format=\"ascii\">\n"

        );



        for(int i=0;i<nodeCount;i++)

        {


            GridNode node =

                    grid.getNode(i);



            Vector3 v =

                    node.getVelocity();



            out.write(

                    v.x+" "

                    +

                    v.y+" "

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
         *
         * Mass
         *
         */


        out.write(

        "<DataArray type=\"Float64\" Name=\"mass\" NumberOfComponents=\"1\" format=\"ascii\">\n"

        );



        for(int i=0;i<nodeCount;i++)

        {


            GridNode node =

                    grid.getNode(i);



            out.write(

                    node.getMass()

                    +

                    "\n"

            );


        }



        out.write(

        "</DataArray>\n"

        );








        /*
         *
         * Force
         *
         */


        out.write(

        "<DataArray type=\"Float64\" Name=\"force\" NumberOfComponents=\"3\" format=\"ascii\">\n"

        );



        for(int i=0;i<nodeCount;i++)

        {


            GridNode node =

                    grid.getNode(i);



            //Vector3 f =  node.getInternalForce().add( node.getExternalForce() ); //Internal + External
            Vector3 f =  node.getInternalForce();
            f.addInPlace(node.getExternalForce());

            out.write(

                    f.x+" "

                    +

                    f.y+" "

                    +

                    f.z

                    +

                    "\n"

            );


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
         * Cells
         *
         * Hexahedral Grid Cell
         *
         * ====================================================
         */


        out.write(

        "<Cells>\n"

        );






        /*
         *
         * connectivity
         *
         */


        out.write(

        "<DataArray type=\"Int32\" Name=\"connectivity\" format=\"ascii\">\n"

        );



        for(int i=0;i<cellCount;i++)

        {

        	GridNode[] nodes =

                    grid.getCell(i)
                        .getNodes();



            for(GridNode node:nodes)

            {

                out.write(
                        node.getId()+" "
                );

            }


            out.write("\n");


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


        for(int i=1;i<=cellCount;i++)

        {


            out.write(

                    (i*8)

                    +

                    "\n"

            );


        }


        out.write(

        "</DataArray>\n"

        );







        /*
         *
         * VTK cell type
         *
         *
         * Hexahedron = 12
         *
         */


        out.write(

        "<DataArray type=\"UInt8\" Name=\"types\" format=\"ascii\">\n"

        );


        for(int i=0;i<cellCount;i++)

        {


            out.write(

                    "12\n"

            );


        }


        out.write(

        "</DataArray>\n"

        );




        out.write(

        "</Cells>\n"

        );






        out.write(

        "</Piece>\n"

        );


        out.write(

        "</UnstructuredGrid>\n"

        );


        out.write(

        "</VTKFile>\n"

        );



        out.close();



    }



}