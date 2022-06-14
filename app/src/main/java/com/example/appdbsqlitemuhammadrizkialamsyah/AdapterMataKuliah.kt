package com.example.appdbsqlitemuhammadrizkialamsyah

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*


class AdapterMataKuliah (
    private val getContext: Context,
    private val customListItem: ArrayList<MataKuliah>
): ArrayAdapter<MataKuliah>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val infLateList = (getContext as Activity).layoutInflater
            listLayout = infLateList.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmMatkul = listLayout.findViewById(R.id.tvNmMatkul)
                tvKdMatkul = listLayout.findViewById(R.id.tvKdMatkul)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmMatkul!!.setText(listItem.nmMatkul)
        holder.tvKdMatkul!!.setText(listItem.kdMatkul)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntriMatkulActivity::class.java)
            i.putExtra("kode", listItem.kdMatkul)
            i.putExtra("nama", listItem.nmMatkul)
            i.putExtra("sks",listItem.sks)
            i.putExtra("sifat", listItem.Sifat)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = DBHelper(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvKdMatkul!!.text
            val nama = holder.tvKdMatkul!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                    Apakah Anda Yakin Akan Menghapus Data ini?
                    
                    $nama [$kode]
                    """.trimIndent())
                setPositiveButton("Ya") { _, _ ->
                    if(db.hapus("$kode"))
                        Toast.makeText(
                            context,
                            "Data mata kuliah berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data mata kuliah gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNmMatkul: TextView? = null
        internal var tvKdMatkul: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null

    }
}
