#version 330

layout(location = 0) in vec2 pos;
layout (location = 1) in vec2 tex;
layout (location = 2) in vec2 trans;
layout (location = 3) in vec2 text;

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

out vec2 texCoords;

void main()
{
    gl_Position = projectionMatrix * viewMatrix * vec4(pos.x+trans.x,pos.y+trans.y, 0.0f, 1.0f);
    texCoords = tex+text;
}