#!/usr/bin/env python
from flask import Flask, redirect, render_template, request, flash
from .forms import MessageForm
from .models import Message
import redis
import os


app = Flask(__name__)
app.config["SECRET_KEY"] = "secret!"

REDIS_HOST = os.getenv("REDIS_HOST") or "localhost"
REDIS_PORT = int(os.getenv("REDIS_PORT") or 6379)
REDIS_SERVER = redis.Redis(host=REDIS_HOST, port=REDIS_PORT, db=0, password="")
QUEUE = os.getenv("QUEUE_KEY") or "queue:my_queue"


@app.route("/", methods=["GET", "POST"])
def index():
    if REDIS_SERVER == None:
        flash("Could not connect to redis server")
        return redirect("/")

    form = MessageForm()
    if form.validate_on_submit():
        message = Message(form.message.data)

        with REDIS_SERVER.pipeline() as pipe:
            while True:
                try:
                    pipe.watch(QUEUE)
                    pipe.multi()
                    pipe.rpush(QUEUE, str(message))
                    pipe.execute()
                    break
                except redis.WatchError:
                    continue

        return redirect("/")

    return render_template("index.html", form=form)

