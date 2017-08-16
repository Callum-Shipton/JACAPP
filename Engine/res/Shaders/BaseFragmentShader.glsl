#version 330

in vec2 texCoords;
out vec4 color;

uniform sampler2D image;
uniform mat4 textureMatrix;

void main()
{
	vec2 texC = (textureMatrix * vec4(texCoords.x, texCoords.y, 0.0f, 1.0f)).xy;
    color = texture(image, texC);
}