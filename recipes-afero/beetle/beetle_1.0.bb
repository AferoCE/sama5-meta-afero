# Copyright (C) 2017 Afero, Inc. All rights reserved

DESCRIPTION = "Afero Bluetooth interface"
SECTION = "examples"
DEPENDS = "bluez5"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

inherit externalsrc autotools update-rc.d
EXTERNALSRC = "${TOPDIR}/../../src_afero/beetle/pkg"

PARALLEL_MAKE = ""

SRC_URI += " file://beetle"
SRC_URI += " file://beetle_watcher"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "beetle"
INITSCRIPT_PARAMS = "defaults 92 8"

do_install_append() {
    echo "do_install() beetle directories and configuration files ...."

    install -d ${D}${sysconfdir}/init.d/

    install -Dm 755 ${WORKDIR}/beetle  ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/beetle_watcher  ${D}${bindir}
}
