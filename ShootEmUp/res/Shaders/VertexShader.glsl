#version 330

layout(location = 0) in vec2 pos;

layout (location = 1) in vec2 tex;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

out vec2 texCoords;
out vec2 position;

void main()
{
	position = vec2(pos.x+modelMatrix[3][0],pos.y+modelMatrix[3][1]);
    gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(pos.x,pos.y, 0.0f, 1.0f);
    texCoords = tex;
}