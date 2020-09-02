#!/bin/bash

source .env
sed -i'.orginal' -e  "s/\$MYSQL_DATABASE/${MYSQL_DATABASE}/g;s/\$MYSQL_USER/${MYSQL_USER}/g;s/\$MYSQL_PASSWORD/${MYSQL_PASSWORD}/g" db/create_message_db.sql
