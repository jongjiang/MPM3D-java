package tw.edu.mpm.mesh;


import tw.edu.mpm.math.Vector3;


/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * Cell.java
 *
 *
 * Background Grid Cell
 *
 *
 * 功能：
 *
 *     1. 儲存8個GridNode
 *     2. 判斷Particle是否在Cell內
 *     3. 計算Shape Function
 *     4. 計算Shape Function Gradient
 *
 * Background Grid Cell
 *
 *
 * 3D Hexahedral Cell
 *
 *
 * Node numbering:
 *
 *
 *          7 -------- 6
 *         /|         /|
 *        / |        / |
 *       4 -------- 5  |
 *       |  3 ------|--2
 *       | /        | /
 *       |/         |/
 *       0 -------- 1
 *
 * ============================================================
 */


public class Cell {



    /*
     * ========================================================
     *
     * Cell ID
     *
     * ========================================================
     */

    private int id;




    /*
     * ========================================================
     *
     * 八個Corner Nodes
     *
     * Hexahedral Element
     *
     * ========================================================
     */


    private GridNode[] nodes;





    /*
     * ========================================================
     *
     * Cell最小與最大座標
     *
     * Bounding Box
     *
     * ========================================================
     */


    private Vector3 min;


    private Vector3 max;






    /*
     * ========================================================
     *
     * Cell尺寸
     *
     * dx dy dz
     *
     * ========================================================
     */


    private Vector3 size;






    /*
     * ========================================================
     *
     * Constructor
     *
     * ========================================================
     */


    public Cell(
            int id,
            GridNode[] nodes
    ){


        this.id=id;


        this.nodes =
                new GridNode[8];



        for(int i=0;i<8;i++){

            this.nodes[i]
                    =
                    nodes[i];

        }




        calculateBoundingBox();


    }







    /*
     * ========================================================
     *
     * 計算Cell範圍
     *
     * ========================================================
     */


    private void calculateBoundingBox(){



        double xmin =
                Double.MAX_VALUE;


        double ymin =
                Double.MAX_VALUE;


        double zmin =
                Double.MAX_VALUE;




        double xmax =
                -Double.MAX_VALUE;


        double ymax =
                -Double.MAX_VALUE;


        double zmax =
                -Double.MAX_VALUE;





        for(GridNode node:nodes){



            Vector3 p =
                    node.getPosition();




            xmin =
                    Math.min(
                            xmin,
                            p.x
                    );


            ymin =
                    Math.min(
                            ymin,
                            p.y
                    );


            zmin =
                    Math.min(
                            zmin,
                            p.z
                    );



            xmax =
                    Math.max(
                            xmax,
                            p.x
                    );


            ymax =
                    Math.max(
                            ymax,
                            p.y
                    );


            zmax =
                    Math.max(
                            zmax,
                            p.z
                    );



        }




        min =
                new Vector3(
                        xmin,
                        ymin,
                        zmin
                );



        max =
                new Vector3(
                        xmax,
                        ymax,
                        zmax
                );



        size =
                max.subtract(min);


    }









    /*
     * ========================================================
     *
     * 判斷Particle是否在Cell
     *
     * ========================================================
     */


    public boolean contains(
            Vector3 position
    ){


        return


        position.x >= min.x &&

        position.x <= max.x &&


        position.y >= min.y &&

        position.y <= max.y &&


        position.z >= min.z &&

        position.z <= max.z;



    }







    /*
     * ========================================================
     *
     * Shape Function
     *
     * trilinear interpolation
     *
     *
     * N_i
     *
     * ========================================================
     */


    public double[] shapeFunction(
            Vector3 position
    ){



        Vector3 xi =
                localCoordinate(position);




        double r =
                xi.x;


        double s =
                xi.y;


        double t =
                xi.z;





        double[] N =
                new double[8];




        N[0]=
        (1-r)*(1-s)*(1-t);



        N[1]=
        r*(1-s)*(1-t);



        N[2]=
        r*s*(1-t);



        N[3]=
        (1-r)*s*(1-t);



        N[4]=
        (1-r)*(1-s)*t;



        N[5]=
        r*(1-s)*t;



        N[6]=
        r*s*t;



        N[7]=
        (1-r)*s*t;



        return N;


    }








    /*
     * ========================================================
     *
     * Global → Local coordinate
     *
     *
     * x → ξ
     *
     * ========================================================
     */


    public Vector3 localCoordinate(
            Vector3 position
    ){



        return new Vector3(


                (position.x-min.x)
                /
                size.x,



                (position.y-min.y)
                /
                size.y,



                (position.z-min.z)
                /
                size.z



        );


    }









    /*
     * ========================================================
     *
     * Shape Function Gradient
     *
     *
     * ∇N
     *
     * ========================================================
     */


    public Vector3[] shapeGradient(
            Vector3 position
    ){


        Vector3 xi =
                localCoordinate(position);



        double r=xi.x;

        double s=xi.y;

        double t=xi.z;





        Vector3[] grad =
                new Vector3[8];




        double dx=size.x;

        double dy=size.y;

        double dz=size.z;





        grad[0]=
        new Vector3(

            -(1-s)*(1-t)/dx,

            -(1-r)*(1-t)/dy,

            -(1-r)*(1-s)/dz

        );



        grad[1]=
        new Vector3(

            (1-s)*(1-t)/dx,

            -r*(1-t)/dy,

            -r*(1-s)/dz

        );



        grad[2]=
        new Vector3(

            s*(1-t)/dx,

            r*(1-t)/dy,

            -r*s/dz

        );



        grad[3]=
        new Vector3(

            -s*(1-t)/dx,

            (1-r)*(1-t)/dy,

            -(1-r)*s/dz

        );



        grad[4]=
        new Vector3(

            -(1-s)*t/dx,

            -(1-r)*t/dy,

            (1-r)*(1-s)/dz

        );



        grad[5]=
        new Vector3(

            (1-s)*t/dx,

            -r*t/dy,

            r*(1-s)/dz

        );



        grad[6]=
        new Vector3(

            s*t/dx,

            r*t/dy,

            r*s/dz

        );



        grad[7]=
        new Vector3(

            -s*t/dx,

            (1-r)*t/dy,

            (1-r)*s/dz

        );




        return grad;


    }









    /*
     * ========================================================
     *
     * Getter
     *
     * ========================================================
     */


    public int getId(){

        return id;

    }




    public GridNode[] getNodes(){

        return nodes;

    }




    public Vector3 getMin(){

        return min;

    }




    public Vector3 getMax(){

        return max;

    }




    public Vector3 getSize(){

        return size;

    }







    @Override

    public String toString(){


        return

        "Cell ID="
        +
        id
        +
        "\nMin="
        +
        min
        +
        "\nMax="
        +
        max;



    }


}