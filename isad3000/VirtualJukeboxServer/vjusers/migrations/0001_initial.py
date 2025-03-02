# Generated by Django 4.0.6 on 2022-08-08 05:49

from django.db import migrations, models
import uuid


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.UUIDField(default=uuid.uuid4, editable=False, primary_key=True, serialize=False)),
                ('created_at', models.DateTimeField(auto_now_add=True)),
                ('updated_at', models.DateTimeField(auto_now=True)),
                ('deleted_at', models.DateTimeField(null=True)),
                ('is_active', models.BooleanField(default=True)),
                ('username', models.CharField(max_length=50)),
                ('can_host', models.BooleanField(default=False)),
            ],
        ),
    ]
