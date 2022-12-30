package attendance.netsurf.netsurfattendance.NewDashboard

import java.util.ArrayList
import java.util.HashMap


    class PayrollItemModel(itemList: List<String>?, itemText: String?) {
        private var nestedList: List<String>? = null
        private var itemText: String? = null
        private var isExpandable = false

        init {
            nestedList = itemList
            this.itemText = itemText
            isExpandable = false
        }

        fun setExpandable(expandable: Boolean) {
            isExpandable = expandable
        }

        fun getNestedList(): List<String>? {
            return nestedList
        }

        fun getItemText(): String? {
            return itemText
        }

        fun isExpandable(): Boolean {
            return isExpandable
        }
    }