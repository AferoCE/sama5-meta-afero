# Copyright (C) 2017 Afero, Inc. All rights reserved

DESCRIPTION = "edge device library"
SECTION = "examples"
DEPENDS = "libevent af-util af-ipc attrd"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI = "git://git@github.com/AferoCE/sama5-binaries-af-edge;protocol=ssh"
SRCREV = "366ab96cf2740316f62d4dc0b940305897755967"

S = "${WORKDIR}/git"

do_install() {
    # Install aflib (libaf_edge.so)
    install -d ${D}${libdir}
    install -Dm 755 ${S}/${BUILD_TARGET}${libdir}/libaf_edge.so.0.0.0 ${D}${libdir}
    ln -s libaf_edge.so.0.0.0 ${D}${libdir}/libaf_edge.so
    ln -s libaf_edge.so.0.0.0 ${D}${libdir}/libaf_edge.so.0

    # Install aflib.h
    install -d ${D}${includedir}
    install -Dm 644 ${S}/${BUILD_TARGET}${includedir}/*.h ${D}${includedir}
}

FILES_${PN} += " ${libdir}/libaf_edge.so ${libdir}/libaf_edge.so.0"
