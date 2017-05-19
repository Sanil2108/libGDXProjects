package com.sanilk.sat;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SATCollisionDetector {

    //The following method finds the projection of vector x along vector y
    //and returns a vector
    public static Vector2 projection(Vector2 x, Vector2 y){
        Vector2 projection=new Vector2(y.x, y.y);

        float dot=Vector2.dot(x.x, x.y, y.x, y.y)/(Vector2.len(y.x, y.y)*Vector2.len(y.x, y.y));
        projection.x = dot*projection.x;
        projection.y = dot*projection.y;

        return projection;
    }

    public static boolean isOverlapping(Vector2 line, Vector2[] A, Vector2[] B){

        float[] Af=new float[A.length];
        float[] Bf=new float[B.length];


        for(int i=0;i<Af.length;i++){
            Vector2 projection = projection(A[i], line);
            Af[i] = A[i].dst(100*line.y, 100*line.x);

//            System.out.println(i+" for A - "+Af[i]);
        }

        for(int i=0;i<Bf.length;i++){
            Vector2 projection = projection(B[i], line);
            Bf[i] = B[i].dst(100*line.y, 100*line.x);

//            System.out.println(i+" for B - "+Bf[i]);
        }

//
//        for(int i=0;i<Af.length;i++){
//            Vector2 projection = projection(A[i], line);
//            Af[i] = Vector2.len(projection.x, projection.y);
//
////            System.out.println(i+" for A - "+Af[i]);
//        }
//
//        for(int i=0;i<Bf.length;i++){
//            Vector2 projection = projection(B[i], line);
//            Bf[i] = Vector2.len(projection.x, projection.y);
//
////            System.out.println(i+" for B - "+Bf[i]);
//        }

        //finding minimum of Af and minimum of Bf
        float minAf=Af[0];
        float maxAf=Af[0];

        float minBf=Bf[0];
        float maxBf=Bf[0];

        for(int i=1;i<Af.length;i++){
            if(minAf>Af[i]){
                minAf=Af[i];
            }
            if(maxAf<Af[i]){
                maxAf=Af[i];
            }
        }

        for(int i=1;i<Bf.length;i++){
            if(minBf>Bf[i]){
                minBf=Bf[i];
            }
            if(maxBf<Bf[i]){
                maxBf=Bf[i];
            }
        }

        if((minBf>maxAf) ||
                (minAf>maxBf)){
            return false;
        }

        return true;
    }

    public static Vector2[] getSides(Polygon r){
        float[] points=r.getTransformedVertices();
        Vector2[] sides=new Vector2[points.length/2];
        //variable k is for indexing sides
        int k=0;
        int i=0;

        for(;k<sides.length;){
            if(k<sides.length-1){
                sides[k]=new Vector2(points[i+2]-points[i], points[i+3]-points[i+1]);
                i+=2;
            }else{
                sides[k]=new Vector2(points[0]-points[points.length-2], points[1]-points[points.length-1]);
            }
            k++;
        }

//        for(int i=0;i<points.length;i++){
//            System.out.println(points[i]);
//        }

        return sides;

    }

    public static Vector2[] getPoints(Polygon p1){
        float[] pointsF=p1.getTransformedVertices();
        Vector2[] points=new Vector2[pointsF.length/2];

        int i=0;
        int k=0;
        for(;i<pointsF.length;){
            points[k]=new Vector2(pointsF[i], pointsF[i+1]);
            k++;
            i+=2;
        }

        return points;
    }

    public static boolean isColliding(Polygon p1, Polygon p2){
        boolean isColliding=false;

        Vector2[] sides1=getSides(p1);
        Vector2[] sides2=getSides(p2);

        Vector2[] points1=getPoints(p1);
        Vector2[] points2=getPoints(p2);

        for(int i=0;i<sides1.length;i++){
            Vector2[] allProjectionsA=new Vector2[points1.length];
            Vector2[] allProjectionsB=new Vector2[points2.length];

            for(int i2=0;i2<allProjectionsA.length;i2++){
                allProjectionsA[i2]=projection(points1[i2], sides1[i]);
            }

            for(int i2=0;i2<allProjectionsB.length;i2++){
                allProjectionsB[i2]=projection(points2[i2], sides1[i]);
            }

            if(isOverlapping(sides1[i], allProjectionsA, allProjectionsB) == false){
                return false;
            }
        }

        for(int i=0;i<sides2.length;i++){
            Vector2[] allProjectionsA=new Vector2[points1.length];
            Vector2[] allProjectionsB=new Vector2[points2.length];

            for(int i2=0;i2<allProjectionsA.length;i2++){
                allProjectionsA[i2]=projection(points1[i2], sides2[i]);
            }

            for(int i2=0;i2<allProjectionsB.length;i2++){
                allProjectionsB[i2]=projection(points2[i2], sides2[i]);
            }

            if(isOverlapping(sides2[i], allProjectionsA, allProjectionsB) == false){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args){
        Vector2 v1=new Vector2(7, 5);
        Vector2 v2=new Vector2(3, 5);

//        Vector2 projection1=projection(v1, v2);
//        Vector2 projection2=projection(v2, v1);
//
//        System.out.println(projection2);

//        Vector2[] A={
//                new Vector2(1, -1),
//                new Vector2(3, -1),
//        };
//
//        Vector2[] B={
//                new Vector2(4, 2)
//        };

//        System.out.println(isOverlapping(new Vector2(2, 1), A, B));

//        float[] points = {
//                1, 1, 1, 2, 3, 2, 3, 1
//        };
//        Vector2[] pointsV = getSides(new Polygon(points));
//        for(int i=0;i<pointsV.length;i++){
//            System.out.println(pointsV[i]);
//        }

        float[] points1={
                0, 1, 6, 5, 6, 6, 1, 5
        };
        float[] points2={
                1, 3, 4, 1, 5.5f, 5.5f, 2, 5.5f
        };


        Polygon p1=new Polygon(points1);
        Polygon p2=new Polygon(points2);

        System.out.println(isColliding(p1, p2));

//        Vector2[] pointsV1=getPoints(p1);
//        for(Vector2 i:pointsV1){
//            System.out.println(i);
//        }
    }

}