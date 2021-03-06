# Copyright 2017-2018 NXP

DESCRIPTION = "i.MX Hantro VPU library"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ab1a30d0cd181e3408077727ea5a2db"

DEPENDS = "virtual/kernel"
do_configure[depends] += "virtual/kernel:do_shared_workdir"

PROVIDES = "virtual/imxvpu"

SRC_URI = " \
    ${FSL_MIRROR}/${PN}-${PV}.bin;fsl-eula=true \
    file://0001-Fix-ion.h-header-inclusion-to-be-standard.patch \
    file://0002-Fix-version.h-inclusion-to-be-from-kernel-build-fold.patch \
"
SRC_URI[md5sum] = "140796ddd6f1be47cffb7e5e2bfe0fb6"
SRC_URI[sha256sum] = "c092a5b0f8897bae54154f58e47b6d2de033da01ee231a8cd779a51bbe962606"

inherit fsl-eula-unpack

PARALLEL_MAKE="-j 1"

PLATFORM_mx8mm = "IMX8MM"
PLATFORM_mx8mq = "IMX8MQ"

do_compile () {
    oe_runmake CROSS_COMPILE="${HOST_PREFIX}" LINUX_KERNEL_BUILD="${STAGING_KERNEL_BUILDDIR}" \
        LINUX_KERNEL_ROOT="${STAGING_KERNEL_DIR}" SDKTARGETSYSROOT="${STAGING_DIR_TARGET}" \
        PLATFORM="${PLATFORM}" all
}

do_install () {
    oe_runmake DEST_DIR="${D}" PLATFORM="${PLATFORM}" install
}

FILES_${PN} += "/unit_tests"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx8mq|mx8mm)"
