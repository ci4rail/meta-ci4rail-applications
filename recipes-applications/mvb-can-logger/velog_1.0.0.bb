SUMMARY = "Vehicle Data Logger"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/ci4rail/velog"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

COMPATIBLE_HOST = "(aarch64).*-linux"

SRC_URI_aarch64 = "https://github.com/ci4rail/mvb-can-logger/releases/download/v${PV}/${BPN}-v${PV}-linux-arm64.tar.gz;name=arm64"
SRC_URI_append = " file://${BPN}.service"

# Use github-release-checksums.sh script in yoct-images repo to update
SRC_URI[arm64.md5sum] = "6535f106d710d24b1678ced85b88edfd"
SRC_URI[arm64.sha256sum] = "90b994fcc398630e14f4c6e311c8636b8854d071b5ab31beade3380a84ad4713"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${BPN} = "${BPN}.service"
FILES_${PN} += "${systemd_system_unitdir}/${BPN}.service"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/${BPN} ${D}${bindir}/${BPN}
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/${BPN}.service ${D}/${systemd_unitdir}/system
}

FILES_${PN} += "${bindir}/${BPN}"
FILES_${PN} += "${systemd_system_unitdir}/${BPN}.service"

inherit systemd
