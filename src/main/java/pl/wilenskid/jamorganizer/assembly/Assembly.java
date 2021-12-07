package pl.wilenskid.jamorganizer.assembly;

public interface Assembly<Model, Bean> {
	Bean toBean(Model model);

	Model toModel(Bean bean);
}
