# Generated by Django 4.0.6 on 2022-08-16 00:56

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('vjsessions', '0002_alter_session_session_password'),
    ]

    operations = [
        migrations.AlterField(
            model_name='session',
            name='session_password',
            field=models.CharField(blank=True, max_length=256, null=True),
        ),
    ]
