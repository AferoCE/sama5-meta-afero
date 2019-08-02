FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit kernel

SRC_URI += " \
        file://af_bt_sama5d2_dt.patch \
       "

SRC_URI += "file://defconfig"

