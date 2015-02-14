#version 330

layout(location = 0) in vec2 pos;

layout (location = 1) in vec2 tex;

uniform mat4 modelMatrix;

uniform mat4 projectionMatrix;

out vec2 texCoords;

void main()
{
    gl_Position = projectionMatrix * modelMatrix * vec4(pos.x,pos.y, 0.0f, 1.0f);
    texCoords = tex;
}