package com.splyza.team

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.splyza.team.data.Roles
import com.splyza.team.data.TeamViewModel
import com.splyza.team.databinding.ActivityInviteBinding

class InviteActivity : AppCompatActivity() {

    inner class RoleAdapter(
        model: TeamViewModel,
        resource: Int,
        roles: Array<String>
    ) :
        ArrayAdapter<String>(this, resource, roles) {

        //ToDO: add corresponding test cases.
        private val member = model.team.value?.members
        private val plan = model.team.value?.plan

        override fun isEnabled(position: Int): Boolean {
            return when (position) {
                // Disable the role option by checking the open slots.
                Roles.Manager.ordinal, Roles.Editor.ordinal, Roles.Member.ordinal -> {
                    plan?.memberLimit ?: 0 > (member?.total ?: 0) - (member?.supporters ?: 0)
                }
                Roles.Readonly.ordinal -> plan?.supporterLimit ?: 0 > member?.supporters ?: 0
                else -> true
            }
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            return if (position == Roles.Readonly.ordinal) {
                // Hide unavailable supporter by returning the dummy view.
                if (plan?.supporterLimit ?: 0 == 0) {
                    View(this@InviteActivity)
                } else {
                    super.getDropDownView(position, null, parent)
                }
            } else super.getDropDownView(position, null, parent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Use data binding to update UI.
        val binding: ActivityInviteBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_invite)
        val model: TeamViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.viewmodel = model
        model.teamId = intent.getStringExtra("team_id")!!

        binding.permissionSpinner.adapter =
            RoleAdapter(
                model,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.roles)
            )

        binding.shareQrCodeBtn.setOnClickListener {
            Intent(this, QrCodeActivity::class.java).apply {
                putExtra("link", model.inviteLink)
                startActivity(this)
            }
        }
        binding.copyLinkBtn.setOnClickListener {
            val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            ClipData.newPlainText("teamUrl", model.inviteLink)?.apply {
                manager.setPrimaryClip(this)
            }
            Toast.makeText(this, model.inviteLink, Toast.LENGTH_LONG).show()
        }
    }
}