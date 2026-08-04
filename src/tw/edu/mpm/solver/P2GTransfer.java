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
 * P2GTransfer.java
 *
 *
 * Particle To Grid Transfer
 *
 *
 * Particle → Grid
 *
 *
 * 功能：
 *
 *     mass transfer
 *     momentum transfer
 *
 *
 * ============================================================
 */


public class P2GTransfer {



    /**
     *
     * Particle to Grid
     *
     *
     * @param particles Material Points
     * @param grid Background Grid
     *
     */


    public static void transfer(
            ParticleSet particles,
            Grid grid
    ){



        /*
         * Step 1
         *
         * 清除Grid
         *
         */


        grid.reset();





        /*
         * Step 2
         *
         * 逐Particle處理
         *
         */


        for(MaterialPoint p:particles){





            /*
             *
             * 找Particle所在Cell
             *
             */


            Cell cell =

                    grid.findCell(

                            p.getPosition()

                    );





            /*
             *
             * Particle超出Grid
             *
             */


            if(cell==null){

                continue;

            }






            /*
             *
             * Shape Function
             *
             */

            double[] N =

                    cell.shapeFunction(

                            p.getPosition()

                    );







            /*
             *
             * 八個Node
             *
             */


            GridNode[] nodes =

                    cell.getNodes();








            /*
             *
             * Mass & Momentum Transfer
             *
             */


            for(int i=0;i<8;i++){



                GridNode node =
                        nodes[i];



                double weight =
                        N[i];





                /*
                 *
                 * m_i += N_ip m_p
                 *
                 */


                node.addMass(

                        weight
                        *
                        p.getMass()

                );





                /*
                 *
                 * p_i += N_ip m_p v_p
                 *
                 */



                Vector3 momentum =


                p.getVelocity()
                .multiply(

                        p.getMass()
                        *
                        weight

                );




                node.addMomentum(

                        momentum

                );




            }



        }








        /*
         *
         * 計算Grid Velocity
         *
         */


        for(GridNode node:grid.getNodes()){


            node.updateVelocity();


        }





    }




}