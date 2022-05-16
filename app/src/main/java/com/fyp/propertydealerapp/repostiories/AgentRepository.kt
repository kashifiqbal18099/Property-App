package com.fyp.propertydealerapp.repostiories

import com.fyp.propertydealerapp.model.AgentModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AgentRepository (mAuth: FirebaseAuth, db: FirebaseFirestore) {

    private var mAuth: FirebaseAuth? = null;
    private var db: FirebaseFirestore? = null;

    init {
        this.mAuth = mAuth;
        this.db = db;
    }

    fun addAgent(agent:AgentModel?,completion: () -> (Unit),onError: (error:String) -> (Unit)){
        var user = mAuth?.currentUser

        mAuth?.createUserWithEmailAndPassword(agent?.email!!,agent?.password!!)?.addOnSuccessListener {
            agent.id  = it.user?.uid!!
            db?.collection("User")?.document(it.user?.uid!!)?.set(agent)?.addOnSuccessListener {
                completion()

            }?.addOnFailureListener {
                onError(it.message!!)
            }
        }?.addOnFailureListener {
            onError(it.message!!)
        }

    }
}