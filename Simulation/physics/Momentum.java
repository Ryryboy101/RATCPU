package org.rtsang.physics;

import org.joml.Vector3f;
import org.rtsang.objectLoader.GameObject;


public class Momentum {

    //J = F * t
    public static Vector3f calculateImpulse(GameObject object, float time, boolean isElastic) {
        float mass = object.getMass();
        Vector3f acceleration = object.getTranslationalKinematics().getAcceleration();
        Vector3f force = new Vector3f(mass * acceleration.x, mass * acceleration.y, mass * acceleration.z);
        return force.mul(time);
    }

    //J = dp
    public static Vector3f calculateVelocity(GameObject one, GameObject two, boolean isElastic) {
        float mass1 = one.getMass();
        float mass2 = two.getMass();

        Vector3f velocity1 = one.getTranslationalKinematics().getVelocity();
        Vector3f velocity2 = two.getTranslationalKinematics().getVelocity();

        Vector3f initialMomentum1 = velocity1.mul(mass1);
        Vector3f initialMomentum2 = velocity2.mul(mass2);

        Vector3f endVelocity;
        Vector3f totalMomentum = initialMomentum1.add(initialMomentum2);
        if (isElastic) {
            //calculates the end velocity of object one
            endVelocity = velocity1.mul((mass1 - mass2) / (mass1 + mass2)).add(velocity2.mul((2 * mass2) / (mass1 + mass2)));
        } else {
            endVelocity = totalMomentum.div(mass1 + mass2);
        }
        //physics is so fun 🙂
        return endVelocity;
    }

}
