package com.example.insta.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.insta.R
import com.example.insta.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(
    var mUsers: List<User>,
    var mContext: Context,
    var isFragment: Boolean = false
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    private lateinit var firebaseUser: FirebaseUser

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageProfile: CircleImageView
        var username: TextView
        var name: TextView
        var btnFollow: Button

        init {
            imageProfile = itemView.findViewById(R.id.image_profile)
            username = itemView.findViewById(R.id.userName)
            name = itemView.findViewById(R.id.fullName)
            btnFollow = itemView.findViewById(R.id.btnFollow)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val user = mUsers[position]
        holder.btnFollow.visibility = View.VISIBLE
        holder.username.text = user.username
        holder.name.text = user.name
        Picasso.get().load(user.imageUrl).placeholder(R.mipmap.ic_launcher)
            .into(holder.imageProfile)


        isFollowed(user.id!!, holder.btnFollow)

        if (user.id == firebaseUser.uid) {
            holder.btnFollow.visibility = View.GONE
        }

    }

    private fun isFollowed(id: String, btnFollow: Button) {
        val reference: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child(firebaseUser.uid).child("following")
        reference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("id").exists()) {
                    btnFollow.text = "Following"
                } else {
                    btnFollow.text = "Follow"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}