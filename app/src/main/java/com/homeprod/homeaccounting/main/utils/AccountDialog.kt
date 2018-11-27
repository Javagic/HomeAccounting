/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 27.11.18 18:25
 */

package com.homeprod.homeaccounting.main.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import com.homeprod.homeaccounting.R
import com.homeprod.homeaccounting.main.data.Account
import com.homeprod.homeaccounting.main.repository.AccountRepository


class AccountDialog : DialogFragment(), DialogInterface.OnClickListener {
    companion object {
        private const val TAG = "AccountDialog"
        private const val KEY_ACCOUNT = "Account"
        fun show(fm: FragmentManager, deleted: Account) {
            AccountDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ACCOUNT, deleted)
                }
                isCancelable = false

            }.show(fm, TAG)
        }
    }

    private lateinit var account: Account
    private var listener: DeleteAccountListener? = null

    override fun onClick(d: DialogInterface?, position: Int) {
        dismiss()
        listener?.onAccountSelected(account, AccountRepository.all().apply { remove(account) }[position])
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is DeleteAccountListener) {
            throw IllegalStateException("${javaClass.simpleName} host activity must implement DeleteAccountListener interface")
        }
        listener = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        account = arguments?.getParcelable(KEY_ACCOUNT)!!
        return AlertDialog.Builder(context!!)
            .apply {
                setTitle(R.string.title_transaction)
                setSingleChoiceItems(
                    AccountRepository.all().apply { remove(account) }.map(Account::toString).toTypedArray(),
                    1,
                    this@AccountDialog
                )
            }
            .create()
    }

    interface DeleteAccountListener {
        fun onAccountSelected(deleted: Account, to: Account)
    }
}
