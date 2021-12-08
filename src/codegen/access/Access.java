package codegen.access;

import codegen.Reg;

/** L’accès MIPS (load/store) à une "variable" de la forme intermédiaire.
 * Matérialise l'allocation mémoire réalisée par allocator*/
public interface Access {
  /** Enregistrer le registre dans la variable.
   * @param reg Le registre
   * @return L'instruction Mips */
  String store(Reg reg);

  /** Charger la variable dans le registre.
   * @param reg Le registre
   * @return L'instruction Mips */
  String load(Reg reg);

  /** Charger la variable dens le registre (alt.).
   * Si la variable est dans a0-a4, on charge depuis la
   * sauvegarde dans la pile plutôt que depuis les registres
   * nécessaire uniquement pour l'appel de fonction.
   * @param reg Le registre
   * @return L'instruction Mips */
  String loadSaved(Reg reg);

  /** Obtenir le registre utilisé par la variable.
   * @return Le registre si la variable utilise un registre */
  Reg getRegister();
}
