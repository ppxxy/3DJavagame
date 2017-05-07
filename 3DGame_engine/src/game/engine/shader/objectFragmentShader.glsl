#version 400 core

in vec2 pass_textureCoordinates;

out vec4 out_Color;

uniform sampler2D diffuseMap;

void main(void){

	vec4 textureColour = texture(diffuseMap, pass_textureCoordinates);

	if(textureColour.a < 0.5){
		discard;
	}
	out_Color = texture(diffuseMap, pass_textureCoordinates);

}