# Copyright (C) 2017 Afero, Inc. All rights reserved

DESCRIPTION = "OTA Manager Sample"
SECTION = "examples"
DEPENDS = "libevent af-util af-ipc attrd"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += " file://otamgr"
SRC_URI += " file://otamgr_watcher"

inherit externalsrc autotools update-rc.d

EXTERNALSRC = "${TOPDIR}/../../src_afero/otamgr/pkg"

PARALLEL_MAKE = ""

EXTRA_OECONF = "BUILD_TARGET=${BUILD_TARGET}"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "otamgr"
INITSCRIPT_PARAMS = "defaults 97 3"

do_install_append() {
    echo "do_install() otamgr directories and configuration files ...."

    install -d ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/otamgr ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/otamgr_watcher ${D}${bindir}
}
