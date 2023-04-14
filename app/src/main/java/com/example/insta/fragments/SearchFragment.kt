package com.example.insta.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.insta.R
import com.example.insta.adapter.UserAdapter
import com.example.insta.model.User
import com.google.firebase.database.*


class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var recyclerView:RecyclerView
    private lateinit var searchBar:AutoCompleteTextView
    private lateinit var mUsers:ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mUsers= arrayListOf()
        val view=inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView=view.findViewById(R.id.recyclerViewUsers)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(context)

        userAdapter=UserAdapter( mUsers ,requireContext(),true)
        recyclerView.adapter=userAdapter

        searchBar=view.findViewById(R.id.search_bar)

        readUsers()
        return view
    }

    private fun readUsers() {
        val reference:DatabaseReference=FirebaseDatabase.getInstance().reference.child("Users")
        reference.addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                mUsers.clear()
                for(index in snapshot.children){
                    val user=index.getValue(User::class.java)
                    if (user != null) {
                        Log.i("Mayank", user.toString())
                        mUsers.add(user)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}