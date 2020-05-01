package com.nabarunsarkar.safe19india.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nabarunsarkar.safe19india.R
import com.nabarunsarkar.safe19india.model.DistrictDetails
import kotlinx.coroutines.InternalCoroutinesApi


class MapRvAdapter(val userList: Map<String, DistrictDetails>, val intent_string: String?) : RecyclerView.Adapter<MapRvAdapter.ViewHolder>() {

    private var keyList: ArrayList<String>? = null
    private var valueList: ArrayList<DistrictDetails>? = null

    @InternalCoroutinesApi
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.list_row, p0, false)

        keyList = ArrayList(userList.keys)
        valueList = ArrayList(userList.values)

        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(p0: ViewHolder, position: Int) {

        p0.name?.text = keyList?.get(position)
        p0.ButtonConfirm?.text = "Confirmed: "+ (valueList?.get(position)?.confirmed)
        p0.date?.visibility = View.GONE
        p0.desc?.visibility = View.GONE
        p0.imageArrow?.visibility = View.GONE
        p0.ButtonActive?.visibility = View.GONE
        p0.ButtonRecovered?.visibility = View.GONE
        p0.ButtonDeath?.visibility = View.GONE
        p0.sub_title?.visibility = View.GONE

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.title)
        val sub_title = itemView.findViewById<TextView>(R.id.sub_title)
        val date = itemView.findViewById<TextView>(R.id.date_text)
        val imageArrow = itemView.findViewById<ImageView>(R.id.imageView)
        val desc = itemView.findViewById<TextView>(R.id.description)


        val ButtonConfirm = itemView.findViewById<Button>(R.id.ButtonConfirm)
        val ButtonActive = itemView.findViewById<Button>(R.id.ButtonActive)
        val ButtonRecovered = itemView.findViewById<Button>(R.id.ButtonRecovered)
        val ButtonDeath = itemView.findViewById<Button>(R.id.ButtonDeath)

    }
}