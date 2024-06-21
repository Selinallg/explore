package com.nolovrcore.explore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.nolovrcore.explore.MainActivity.Companion.TAG
import com.nolovrcore.explore.ui.theme.ExploreTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "_MainActivity"

    }

    private lateinit var context : Context;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this;
        setContent {
            ExploreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android",context)
                }
            }
        }
    }

    override fun addMenuProvider(provider: MenuProvider, owner: LifecycleOwner, state: Lifecycle.State) {
        TODO("Not yet implemented")
    }
}

@Composable
fun Greeting(name: String,context: Context,  modifier: Modifier.Companion = Modifier) {

    Column {

        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        Button(onClick = {
            Log.e(TAG, "Greeting: ================================")
            val intent = Intent(context, FileAccessActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(
                text = "按钮",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExploreTheme {
      //  Greeting("Android")
    }
}