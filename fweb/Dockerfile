FROM python:3.7.9-buster

ENV PYTHONUNBUFFERED 1
RUN mkdir /app
WORKDIR /app
RUN pip install  virtualenv
RUN virtualenv  venv
COPY entrypoint.sh /.
COPY run.py /app/.
COPY app /app/.
RUN pip install -r requirements.txt

ENTRYPOINT ["bash", "/entrypoint.sh"]
CMD ["flask", "run", "--host=0.0.0.0", "--port=5000"]
