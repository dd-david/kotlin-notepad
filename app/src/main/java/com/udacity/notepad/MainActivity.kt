package com.udacity.notepad

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.udacity.notepad.crud.CreateActivity
import com.udacity.notepad.recycler.NotesAdapter
import com.udacity.notepad.util.SpaceItemDecoration

class MainActivity : AppCompatActivity() {

    private var recycler: RecyclerView? = null

    /*
    * https://kotlinlang.org/docs/reference/keyword-reference.html#modifier-keywords
    * modify keywords
    *   kotlin : public override
    *   java   : @Override public
    *
    * */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { startActivity(CreateActivity.get(this@MainActivity)) }

        recycler = findViewById(R.id.recycler)
        /*
        * https://kotlinlang.org/docs/reference/null-safety.html#the--operator
        * !! Operation : not-null assertion operation
        *                if null, throws an exception
        * */
        recycler!!.layoutManager = LinearLayoutManager(this)
        recycler!!.addItemDecoration(SpaceItemDecoration(this, R.dimen.margin_small))
        recycler!!.adapter = NotesAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    public override fun onDestroy() {
        super.onDestroy()
        recycler!!.adapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun refresh() {
        /*
        * https://kotlinlang.org/docs/reference/typecasts.html#unsafe-cast-operator
        * as  : <unsafe cast> means if the cast isn't possible, throw an exception
        *       (ex) val x : String = y as String     // if y is null, exception will be thrown
        *            val x : String? = y as String?   // if y is null, String? may help to cast
        *
        * as? : <safe(nullable) cast>, to avoid an exception being thrown,
        *       which returns null on failure
        *       (ex) val x : String? = y as? String   // right side of <as?> may return nullable on failure
        * */
        (recycler!!.adapter as NotesAdapter).refresh()
    }
}
