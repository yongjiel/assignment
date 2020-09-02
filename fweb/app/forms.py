from flask_wtf import FlaskForm
from wtforms import TextAreaField, SubmitField
from wtforms.validators import DataRequired


class MessageForm(FlaskForm):
    message = TextAreaField('message', render_kw={"rows": 5, "cols": 50}, validators=[DataRequired()])
    submit = SubmitField('Submit')
