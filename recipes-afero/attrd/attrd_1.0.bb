# Copyright (C) 2017 Afero, Inc. All rights reserved

DESCRIPTION = "Afero Attribute Deamon"
SECTION = "examples"
DEPENDS = "libevent af-util af-ipc"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

inherit externalsrc autotools update-rc.d
EXTERNALSRC = "${TOPDIR}/../../src_afero/attrd/pkg"

PARALLEL_MAKE = ""

EXTRA_OECONF = "BUILD_TARGET=${BUILD_TARGET}"

SRC_URI += " file://attrd"
SRC_URI += " file://attrd_watcher"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "attrd"
INITSCRIPT_PARAMS = "defaults 60 40"

do_install_append() {
    echo "do_install() attrd directories and configuration files ...."

    install -d ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/attrd  ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/attrd_watcher  ${D}${bindir}

}
