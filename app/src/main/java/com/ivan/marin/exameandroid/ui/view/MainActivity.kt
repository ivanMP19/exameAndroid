package com.ivan.marin.exameandroid.ui.view

import android.Manifest
import android.annotation.TargetApi
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.ivan.marin.exameandroid.R
import com.ivan.marin.exameandroid.animations.ZoomOutPageTransformer
import com.ivan.marin.exameandroid.databinding.ActivityMainBinding
import com.ivan.marin.exameandroid.ui.adapters.ViewPagerCarruselAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.HashMap

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 123

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        validarPermisos()

        initUi()
        setUpBottomBar()
    }

    private fun initUi() {
        binding.vpPrincipal.adapter = ViewPagerCarruselAdapter(this,3,1)
        binding.vpPrincipal.setPageTransformer(ZoomOutPageTransformer())
        binding.vpPrincipal.setCurrentItem(0, false)
        binding.vpPrincipal.offscreenPageLimit = 3

        binding.vpPrincipal.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position){
                    0->
                        binding.bottomNav.menu.findItem(R.id.action_apirest).isChecked = true
                    1->
                        binding.bottomNav.menu.findItem(R.id.action_map).isChecked = true
                    2->
                        binding.bottomNav.menu.findItem(R.id.action_imagen).isChecked = true
                }

            }
        })
    }

    private fun setUpBottomBar(){
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_apirest -> {
                    binding.vpPrincipal.currentItem = 0
                    true
                }
                R.id.action_map -> {
                    binding.vpPrincipal.currentItem = 1
                    true
                }
                R.id.action_imagen -> {
                    binding.vpPrincipal.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun validarPermisos() {
        val permissionsList: MutableList<String> = ArrayList()
        agregarPermiso(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION)
        agregarPermiso(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionsList.isNotEmpty()) {
            requestPermissions(
                permissionsList.toTypedArray(),
                REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS
            )
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun agregarPermiso(permissionsList: MutableList<String>, permission: String) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)
            shouldShowRequestPermissionRationale(permission)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            val perms: MutableMap<String?, Int> = HashMap()
            llenaListaPermisos(perms, permissions, *grantResults)
            if (!isTienePermisos(perms)) {
                var isExistePermisoNegado = false
                for (i in permissions.indices) {
                    if (shouldShowRequestPermissionRationale(permissions[i]!!) && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        isExistePermisoNegado = true
                    }
                }
                if (isExistePermisoNegado) {
                    val alertDialog: AlertDialog = this.let {
                        val builder = AlertDialog.Builder(it)
                        builder.apply {
                            setPositiveButton(R.string.ok) { _, _ ->
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:$packageName")
                                )
                                intent.addCategory(Intent.CATEGORY_DEFAULT)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }

                        }
                        builder.setMessage(resources.getString(R.string.activate_prompt_message))
                        builder.setTitle(resources.getString(R.string.aceptarPermisos))
                        builder.setCancelable(false)
                        builder.create()
                        builder.show()
                    }

                }
            }
        }
    }

    private fun llenaListaPermisos(perms: MutableMap<String?, Int>, permissions: Array<String?>, vararg grantResults: Int) {
        perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
        perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
        for (i in permissions.indices) {
            perms[permissions[i]] = grantResults[i]
        }
    }

    private fun isTienePermisos(permissions: Map<String?, Int>): Boolean {
        return permissions[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED &&
                permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
    }

}