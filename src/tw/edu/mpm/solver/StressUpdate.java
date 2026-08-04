package tw.edu.mpm.solver;


import tw.edu.mpm.mesh.Cell;
import tw.edu.mpm.mesh.Grid;
import tw.edu.mpm.mesh.GridNode;

import tw.edu.mpm.math.Matrix3;
import tw.edu.mpm.math.Tensor3;
import tw.edu.mpm.math.Vector3;

import tw.edu.mpm.particle.MaterialPoint;
import tw.edu.mpm.particle.ParticleSet;

import tw.edu.mpm.util.MathUtil;



/**
 * ============================================================
 *
 * MPM3D-Java
 *
 * StressUpdate.java
 *
 *
 * Constitutive Model
 *
 *
 * Linear Elastic Material
 *
 *
 * 功能：
 *
 *     1. 計算 velocity gradient
 *     2. 計算 strain increment
 *     3. 更新 stress
 *
 *
 * ============================================================
 */


public class StressUpdate {



    /*
     * Young modulus
     */

    private double young;



    /*
     * Poisson ratio
     */

    private double poisson;




    /*
     * Lamé parameter
     */

    private double lambda;


    private double mu;





    /**
     *
     * Constructor
     *
     */


    public StressUpdate(
            double young,
            double poisson
    ){


        this.young =
                young;


        this.poisson =
                poisson;



        lambda =
                MathUtil.lameLambda(
                        young,
                        poisson
                );


        mu =
                MathUtil.shearModulus(
                        young,
                        poisson
                );



    }







    /**
     *
     * Update Stress
     *
     *
     * σ(n+1)
     *
     * =
     *
     * σ(n)+Δσ
     *
     */


    public void update(
            ParticleSet particles,
            Grid grid,
            double dt
    ){



        for(MaterialPoint p:particles){



            Cell cell =

                    grid.findCell(

                            p.getPosition()

                    );



            if(cell==null){

                continue;

            }






            /*
             *
             * 計算 Velocity Gradient
             *
             *
             * L=∇v
             *
             */


            Matrix3 velocityGradient =


                    computeVelocityGradient(

                            p,

                            cell

                    );






            /*
             *
             * Strain rate
             *
             *
             * D=0.5(L+LT)
             *
             */


            Tensor3 strainRate =


                    computeStrainRate(

                            velocityGradient

                    );






            /*
             *
             * strain increment
             *
             *
             * Δε=DΔt
             *
             */


            Tensor3 strainIncrement =


                    strainRate.multiply(dt);






            /*
             *
             * Stress increment
             *
             *
             * Δσ
             *
             */


            Tensor3 stressIncrement =


                    elasticStress(

                            strainIncrement

                    );






            Tensor3 newStress =


                    p.getStress()
                     .add(
                         stressIncrement
                     );





            p.setStress(

                    newStress

            );



            p.setStrain(

                    p.getStrain()
                     .add(
                         strainIncrement
                     )

            );




        }




    }








    /**
     *
     * Velocity Gradient
     *
     *
     * L = Σ vi ⊗ ∇Ni
     *
     */


    private Matrix3 computeVelocityGradient(
            MaterialPoint p,
            Cell cell
    ){


        Matrix3 L =
                new Matrix3();



        double[] N =

                cell.shapeFunction(
                        p.getPosition()
                );



        Vector3[] grad =

                cell.shapeGradient(
                        p.getPosition()
                );



        GridNode[] nodes =

                cell.getNodes();






        for(int i=0;i<8;i++){



            Vector3 v =
                    nodes[i]
                    .getVelocity();




            Vector3 g =
                    grad[i];




            L.addOuterProduct(

                    v,

                    g

            );



        }





        return L;



    }








    /**
     *
     * Strain Rate
     *
     *
     * D=0.5(L+LT)
     *
     */


    private Tensor3 computeStrainRate(
            Matrix3 L
    ){



        Matrix3 LT =
                L.transpose();




        Matrix3 D =


                L.add(LT)
                 .multiply(
                         0.5
                 );



        return

        Tensor3.fromMatrix(
                D
        );


    }








    /**
     *
     * Linear Elastic Stress
     *
     *
     * σ=λtr(ε)I+2με
     *
     */


    private Tensor3 elasticStress(
            Tensor3 strain
    ){



        double trace =

                strain.trace();




        Tensor3 stress =


                Tensor3.zero();





        /*
         *
         * λ tr(ε) I
         *
         */


        stress.addDiagonal(

                lambda*trace

        );





        /*
         *
         * 2με
         *
         */


        stress.add(

                strain.multiply(
                        2*mu
                )

        );





        return stress;



    }






    public double getYoung(){


        return young;


    }



    public double getPoisson(){


        return poisson;


    }



    public double getLambda(){


        return lambda;


    }



    public double getMu(){


        return mu;


    }




}