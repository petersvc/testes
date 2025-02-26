#!/bin/bash
cp ./.devcontainer/config/resources/application-dev.yml /apollo-api/src/main/resources/
mkdir /home/ubuntu/.config
cp ./.devcontainer/config/starship/starship.toml /home/ubuntu/.config/starship.toml
# mkdir -p /usr/share/fonts/truetype/jetbrainsMono
# cp .devcontainer/config/JetBrainsMonoNerdFont-SemiBold.ttf /usr/share/fonts/truetype/jetbrainsMono/JetBrainsMonoNerdFont-SemiBold.ttf
# sh ./.devcontainer/scripts/node-lts.sh
echo 'Post create commands executed!'
