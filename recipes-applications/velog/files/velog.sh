#!/bin/bash

# Use cases:
# 1. Get data by exchanging the SD card
#    Call this script with the argument "change-card"
# 2. Get data via network
#    Extract the sdcard data via network e.g. winSCP
#    Call this script with the argument "clear-card"


sdcard_mount_point="/media/sdcard"
systemd_service="velog.service"

function usage () {
    echo "Usage: $0 [change-card|clear-card|show-logs]"
    exit 1
}

function stop_service () {
    echo "Stopping service"
    systemctl stop $systemd_service
    if [ $? -ne 0 ]; then
        echo "Service stop failed"
        exit 1
    fi
}

function start_service () {
    echo "Starting service"
    systemctl start $systemd_service
    if [ $? -ne 0 ]; then
        echo "Service start failed"
        exit 1
    fi
}

function change_card () {
    stop_service
    umount $sdcard_mount_point
    if [ $? -ne 0 ]; then
        echo "Card unmount failed"
        exit 1
    fi
    echo "Please remove old card and insert the new card"
    read -p "Press enter to continue, CTRL-C to abort"
    echo "Rebooting system now to mount the new card"
    reboot
}

function clear_card () {
    stop_service
    echo "This will delete all data on the card."
    read -p "Press enter to continue, CTRL-C to abort"
    rm -rf $sdcard_mount_point/*
    start_service
}

function show_logs () {
    journalctl -u $systemd_service
}

# check command line arguments
if [ $# -ne 1 ]; then
    usage
    exit 1
fi

case $1 in
    change-card)
        change_card
        ;;
    clear-card)
        clear_card
        ;;
    show-logs)
        show_logs
        ;;
    *)
        usage
        exit 1
        ;;
esac
