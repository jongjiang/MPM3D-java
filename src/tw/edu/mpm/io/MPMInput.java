package tw.edu.mpm.io;


import java.io.FileReader;
import java.io.IOException;


import com.google.gson.Gson;


/**
 * ============================================================
 *
 * MPMInput.java
 *
 *
 * MPM Simulation Input Reader
 *
 *
 * 功能：
 *
 *     讀取 input.json
 *
 *
 *     Grid Parameters
 *
 *     Material Parameters
 *
 *     Time Parameters
 *
 *     Solver Parameters
 *
 *
 * ============================================================
 */


public class MPMInput {



    /*
     * ========================================================
     *
     * Inner classes
     *
     * 對應 JSON 結構
     *
     * ========================================================
     */



    public static class GridConfig{


        public int nx;

        public int ny;

        public int nz;


        public double dx;


    }





    public static class MaterialConfig{


        public double density;


        public double young;


        public double poisson;



    }







    public static class TimeConfig{


        public double dt;


        public double endTime;


    }








    public static class SolverConfig{


        public double gravity;


    }







    /*
     * ========================================================
     *
     * JSON members
     *
     * ========================================================
     */



    public GridConfig grid;


    public MaterialConfig material;


    public TimeConfig time;


    public SolverConfig solver;








    /*
     * ========================================================
     *
     * Read JSON file
     *
     * ========================================================
     */


    public static MPMInput read(

            String filename

    )
    throws IOException
    {


        Gson gson =
                new Gson();



        FileReader reader =
                new FileReader(filename);




        MPMInput input =

                gson.fromJson(

                        reader,

                        MPMInput.class

                );



        reader.close();



        return input;


    }









    /*
     * ========================================================
     *
     * Print information
     *
     * ========================================================
     */



    public void print(){



        System.out.println(

                "========== MPM INPUT =========="

        );



        System.out.println(

                "Grid: "

                +

                grid.nx

                +

                " x "

                +

                grid.ny

                +

                " x "

                +

                grid.nz

        );



        System.out.println(

                "Cell size = "

                +

                grid.dx

        );





        System.out.println(

                "Material Density = "

                +

                material.density

        );



        System.out.println(

                "Young modulus = "

                +

                material.young

        );



        System.out.println(

                "Poisson = "

                +

                material.poisson

        );




        System.out.println(

                "dt = "

                +

                time.dt

        );



        System.out.println(

                "End Time = "

                +

                time.endTime

        );



        System.out.println(

                "Gravity = "

                +

                solver.gravity

        );



        System.out.println(

                "=============================="

        );


    }







}