import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuGrid {
	private int N;
	private Element[][] element;

	public SudokuGrid() {
		N = 9;
		this.element = new Element[9][9];
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				element[i][j] = new Element(0);
			}
		}
	}
	public void changeGrid(int[] lista) {
		int brojac=0;
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				element[i][j].setNumber(lista[brojac++]);
			}
		}
	}
	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}

	public Element[][] getElement() {
		return element;
	}
	public void setElement(Element[][] element) {
		this.element = element;
	}
	public void printElements() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print(element[i][j].getNumber()+" ");
			}
			System.out.println();
		}
	}
	public void refresh() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(element[i][j].getNumber()==0) {
					for(int k=0; k<N; k++) {
						if(k!=j) {
							element[i][j].getUsed().remove((Integer)element[i][k].getNumber());
						}
						if(k!=i) {
							element[i][j].getUsed().remove((Integer)element[k][j].getNumber());
						}
					}
					for(int k=(i/3)*3; k<(i/3)*3+3; k++) {
						for(int l=(j/3)*3; l<(j/3)*3+3; l++) {
							if(i!=k || j!=l) {
								element[i][j].getUsed().remove((Integer)element[k][l].getNumber());
							}
						}
					}
				}
			}
		}
	}
	public int posljednjiSlobodan() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(element[i][j].getNumber()==0 && element[i][j].getUsed().size()==1) {
					element[i][j].setNumber(element[i][j].getUsed().get(0));
					System.out.println("Na poziciji (" +i+","+j+") je postavljen broj " + element[i][j].getNumber() + " jer se svi ostali brojevi vec nalaze u tom redu/koloni ili kvadratu");
					return 1;
				}
			}
		}
		return 0;
	}
	public int jediniMoguci() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(element[i][j].getNumber()==0) {
					for(int k=0; k<element[i][j].getUsed().size(); k++) {
						int sadrzi = 0;
						for(int l=0; l<N; l++) {
							if(l!=i) {
								if(element[l][j].getNumber()==element[i][j].getUsed().get(k) || element[l][j].getUsed().contains(element[i][j].getUsed().get(k))) {
									sadrzi=1;
									break;
								}
							}
						}
						if(sadrzi==0) {
							element[i][j].setNumber(element[i][j].getUsed().get(k));
							System.out.println("Na poziciji (" +i+","+j+") je postavljen broj " + element[i][j].getNumber() + " jer je to jedina moguca opcija za taj broj u toj koloni/redu");
							return 1;
						}
						sadrzi = 0;
						for(int l=0; l<N; l++) {
							if(l!=j) {
								if(element[i][l].getNumber()==element[i][j].getUsed().get(k) || element[i][l].getUsed().contains(element[i][j].getUsed().get(k))) {
									sadrzi=1;
									break;
								}
							}
						}
						if(sadrzi==0) {
							element[i][j].setNumber(element[i][j].getUsed().get(k));
							System.out.println("Na poziciji (" +i+","+j+") je postavljen broj " + element[i][j].getNumber() + " jer je to jedina moguca opcija za taj broj u toj koloni/redu");
							return 1;
						}
						sadrzi = 0;
						for(int l=(i/3)*3; l<(i/3)*3+3; l++) {
							for(int m=(j/3)*3; m<(j/3)*3+3; m++) {
								if(i!=l || j!=m) {
									if(element[l][m].getNumber()==element[i][j].getUsed().get(k) || element[l][m].getUsed().contains(element[i][j].getUsed().get(k))) {
										sadrzi=1;
										break;
									}
								}
							}
						}
						if(sadrzi==0) {
							element[i][j].setNumber(element[i][j].getUsed().get(k));
							System.out.println("Na poziciji (" +i+","+j+") je postavljen broj " + element[i][j].getNumber() + " jer je to jedina moguca opcija za taj broj u datom kvadratu");
							return 1;
						}
					}
				}
			}
		}
		return 0;
	}
	
	public int ocigledniParovi() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(element[i][j].getUsed().size()==2) {
					for(int k = i; k<N; k++) {
						if(k!=i) {
							int izmjena = 0;
							if(element[k][j].getUsed().equals(element[i][j].getUsed())) {
								for(int l=0; l<N; l++) {
									if(l!=i && k!=l) {
										int x = element[l][j].getUsed().size();
										element[l][j].getUsed().removeAll(element[i][j].getUsed());
										int y = element[l][j].getUsed().size();
										if(x>y) izmjena++;
									}
								}
								if(izmjena>0) {
								System.out.println("Posto se u redu broj " + j + " nalaze dva elementa koja imaju par " + element[i][j].getUsed() + " iz ostalih elemenata u tom redu je uklonjen taj par");
								break;
								}
							}
						}
					}
				
					for(int k = j; k<N; k++) {
						if(k!=j) {
							int izmjena=0;
							if(element[i][k].getUsed().equals(element[i][j].getUsed())) {
								for(int l=0; l<N; l++) {
									if(l!=j && k!=l) {
										int x = element[i][l].getUsed().size();
										element[i][l].getUsed().removeAll(element[i][j].getUsed());
										int y = element[i][l].getUsed().size();
										if(x>y) izmjena++;
									}
								}
								if(izmjena>0) {
									System.out.println("Posto se u koloni broj " + i + " nalaze dva elementa koja imaju par " + element[i][j].getUsed() + " iz ostalih elemenata u tom redu je uklonjen taj par");
									break;
								}
							}
						}
					}
					for(int k=(i/3)*3; k<(i/3)*3+3; k++) {
						for(int l=(j/3)*3; l<(j/3)*3+3; l++) {
							if(i!=k || j!=l) {
								int izmjena=0;
								if(element[k][l].getUsed().equals(element[i][j].getUsed())) {
									for(int m=(i/3)*3; m<(i/3)*3+3; m++) {
										for(int n=(j/3)*3; n<(j/3)*3+3; n++) {
											if((i!=m || j!=n) && (k!=m || l!=n)) {
												int x = element[m][n].getUsed().size();
												element[m][n].getUsed().removeAll(element[i][j].getUsed());
												int y = element[m][n].getUsed().size();
												if(x>y) izmjena++;
											}
										}
									}
									if(izmjena>0) {
										System.out.println("Posto se u kavadratu sa pocetkom u  (" +(i/3)*3+","+ (j/3)*3 + ") nalaze dva elementa koja imaju par " + element[i][j].getUsed() + " iz ostalih elemenata u tom kvadratu je uklonjen taj par");
										break;
									}
								}
								
							}
						}
					}
				}
			}
		}
		return posljednjiSlobodan();
	}
	public int ocigledneTrojke() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(element[i][j].getUsed().size()==2) {
					for(int k=i+1; k<N; k++) {
						if(element[k][j].getUsed().size()==2 && !element[k][j].getUsed().equals(element[i][j].getUsed())) {
							int uslov=0;
							for(int l=0; l<2; l++) {
								if(element[i][j].getUsed().contains(element[k][j].getUsed().get(l))) {
									uslov++;
								}
							}
							if(uslov==0) break;
							for(int l=0; l<N; l++) {
								if(l!=k && l!=i) {
									if(element[l][j].getUsed().size()==2 && ((element[k][j].getUsed().contains(element[l][j].getUsed().get(0)) && element[i][j].getUsed().contains(element[l][j].getUsed().get(1))) || (element[k][j].getUsed().contains(element[l][j].getUsed().get(1)) && element[i][j].getUsed().contains(element[l][j].getUsed().get(0)))) ) {
										int promjena = 0;
										for(int m=0; m<N; m++) {
											if(m!=i && m!=k && m!=l) {
												int x = element[m][j].getUsed().size();
												element[m][j].getUsed().removeAll(element[i][j].getUsed());
												element[m][j].getUsed().removeAll(element[k][j].getUsed());
												int y = element[m][j].getUsed().size();
												if(x>y) promjena++;
											}
										}
										if(promjena>0) {
											System.out.println("Posto se u redu broj " + j + " nalaze tri elementa koja imaju parove " + element[i][j].getUsed() + " " + element[k][j].getUsed()+" " +element[l][j].getUsed()+ " iz ostalih elemenata u tom redu su uklonjeni ti parovi");
											break;
										}
									}
								}
							}
						}
					}
					for(int k=j+1; k<N; k++) {
						if(element[i][k].getUsed().size()==2 && !element[i][k].getUsed().equals(element[i][j].getUsed())) {
							int uslov=0;
							for(int l=0; l<2; l++) {
								if(element[i][j].getUsed().contains(element[i][k].getUsed().get(l))) {
									uslov++;
								}
							}
							if(uslov==0) break;
							for(int l=0; l<N; l++) {
								if(l!=k && l!=j) {
									if(element[i][l].getUsed().size()==2 && ((element[i][k].getUsed().contains(element[i][l].getUsed().get(0)) && element[i][j].getUsed().contains(element[i][l].getUsed().get(1))) || (element[i][k].getUsed().contains(element[i][l].getUsed().get(1)) && element[i][j].getUsed().contains(element[i][l].getUsed().get(0)))) ) {
										int promjena = 0;
										for(int m=0; m<N; m++) {
											if(m!=j && m!=k && m!=l) {
												int x = element[i][m].getUsed().size();
												element[i][m].getUsed().removeAll(element[i][j].getUsed());
												element[i][m].getUsed().removeAll(element[i][k].getUsed());
												int y = element[i][m].getUsed().size();
												if(x>y) promjena++;
											}
										}
										if(promjena>0) {
											System.out.println("Posto se u koloni broj " + i + " nalaze tri elementa koja imaju parove " + element[i][j].getUsed() + " " + element[i][k].getUsed()+" " +element[i][l].getUsed()+ " iz ostalih elemenata u tom redu su uklonjeni ti parovi");
											break;
										}
									}
								}
							}
						}
					}
					for(int k=(i/3)*3; k<(i/3)*3+3; k++) {
						for(int l=(j/3)*3; l<(j/3)*3+3; l++) {
							if(element[k][l].getUsed().size()==2 && !element[k][l].getUsed().equals(element[i][j].getUsed())) {
								int uslov=0;
								for(int m=0; m<2; m++) {
									if(element[i][j].getUsed().contains(element[k][l].getUsed().get(m))) {
										uslov++;
									}
								}
								if(uslov==0) break;
								for(int m = (i/3)*3; m<(i/3)*3+3; m++) {
									for(int n = (j/3)*3; n<(j/3)*3+3; n++) {
										if((m!=i || n!=j) && (m!=k || n!=l)) {
											if(element[m][n].getUsed().size()==2 && ((element[k][l].getUsed().contains(element[m][n].getUsed().get(0)) && element[i][j].getUsed().contains(element[m][n].getUsed().get(1))) || (element[k][l].getUsed().contains(element[m][n].getUsed().get(1)) && element[i][j].getUsed().contains(element[m][n].getUsed().get(0)))) ) {
												int promjena = 0;
												for(int o = (i/3)*3; o<(i/3)*3+3; o++) {
													for(int p = (j/3)*3; p<(j/3)*3+3; p++) {
														if((o!=i || p!=j) && (o!=k || p!=l) && (o!=m || p!=n)) {
															int x = element[o][p].getUsed().size();
															element[o][p].getUsed().removeAll(element[i][j].getUsed());
															element[o][p].getUsed().removeAll(element[k][l].getUsed());
															int y = element[o][p].getUsed().size();
															if(x>y) promjena++;
														}
													}
												}
												if(promjena>0) {
													System.out.println("Posto se u kvadratu na poziciji (" + (i/3)*3 + "," +(j/3)*3  + ") nalaze tri elementa koja imaju parove " + element[i][j].getUsed() + " " + element[k][l].getUsed()+" " +element[m][n].getUsed()+ " iz ostalih elemenata u tom kvadratu su uklonjeni ti parovi");
													break;
												}
											}

										}
									}
								}
							}
						}
					}
				}
			}
		}

		return posljednjiSlobodan();
	}
	public int skriveniParovi() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(element[i][j].getNumber()==0) {
					List<Integer> lista = new ArrayList();
					for(int k=0; k<element[i][j].getUsed().size(); k++) {
						
						for(int l=k+1; l<element[i][j].getUsed().size(); l++) {
							lista.clear();
							lista.add(element[i][j].getUsed().get(k));
							lista.add(element[i][j].getUsed().get(l));
							int pojavljivanje = 0, posljednje=0;
							for(int m=0; m<N; m++) {
								if(m!=i && element[m][j].getNumber()==0) {
									if(element[m][j].getUsed().contains(lista)) {
										pojavljivanje++;
										posljednje = m;
									}
								}
							}
							if(pojavljivanje==1) {
								element[i][j].getUsed().clear();
								element[i][j].getUsed().addAll(lista);
								element[posljednje][j].getUsed().clear();
								element[posljednje][j].getUsed().addAll(lista);
								System.out.println("Posto se u redu " + j + " nalazi skriveni par " + lista + " iz elemenata koji imaju taj par su uklonjeni svi ostali elementi");
							}
							pojavljivanje = 0;
							posljednje = 0;
							for(int m=0; m<N; m++) {
								if(m!=j && element[i][m].getNumber()==0) {
									if(element[i][m].getUsed().contains(lista)) {
										pojavljivanje++;
										posljednje = m;
									}
								}
							}
							if(pojavljivanje==1) {
								element[i][j].getUsed().clear();
								element[i][j].getUsed().addAll(lista);
								element[i][posljednje].getUsed().clear();
								element[i][posljednje].getUsed().addAll(lista);
								System.out.println("Posto se u koloni " + i + " nalazi skriveni par " + lista + " iz elemenata koji imaju taj par su uklonjeni svi ostali elementi");
								break;
							}
						}
					}
				}
			}
		}
		return jediniMoguci();
	}
	public int pokazujuciParovi() {
		for(int i=0; i<=6; i+=3) {
			for(int j=0; j<=6; j+=3) {
				for(int k=1; k<=9; k++) {
					int brojac=0;
					for(int l=i; l<i+3; l++) {
						for(int m=j; m<j+3; m++) {
							if(element[l][m].getNumber()==0 && element[l][m].getUsed().contains(k)) brojac++;
						}
					}
					if(brojac==2) {
						for(int l=i; l<i+3; l++) {
							int brojac2=0;
							for(int m=j; m<j+3; m++) {
								if(element[l][m].getNumber()==0 && element[l][m].getUsed().contains(k)) brojac2++;
							}
							if(brojac2==2) {
								int promjena = 0;
								for(int m=0; m<N; m++) {
									Integer a = k;
									if(m<j || m>=j+3)
									if(element[l][m].getNumber()==0 && element[l][m].getUsed().contains((Object)a)) {
										element[l][m].getUsed().remove((Object)a);
										promjena++;
									}
								}
							if(promjena>0) {
								System.out.println("Posto u bloku sa pocetkom u ("+ i+","+j+") postoje dva elementa koja mogu biti broj " + k+ " i nalaze se u istom redu, znaci da je jedan od ta dva taj broj, a ostali iz tog reda mogu da se uklone" );
								break;
							}
							}
						}
						for(int l=j; l<j+3; l++) {
							int brojac2=0;
							for(int m=i; m<i+3; m++) {
								if(element[m][l].getNumber()==0 && element[m][l].getUsed().contains(k)) brojac2++;
							}
							if(brojac2==2) {
								int promjena = 0;
								for(int m=0; m<N; m++) {
									Integer a = k;
									if(m<i || m>=i+3)
									if(element[m][l].getNumber()==0 && element[m][l].getUsed().contains((Object)a)) {
										element[m][l].getUsed().remove((Object)a);
										promjena++;
									}
								}
								if(promjena>0) {
									System.out.println("Posto u bloku sa pocetkom u ("+ i+","+j+") postoje dva elementa koja mogu biti broj " + k+ " i nalaze se u istoj koloni, znaci da je jedan od ta dva taj broj, a ostali iz te kolone mogu da se uklone" );
									break;
								}
							}
							
						}
					}
				}
				
			}
		}
		return posljednjiSlobodan();
	}
	public int pokazujuceTrojke() {
		for(int i=0; i<=6; i+=3) {
			for(int j=0; j<=6; j+=3) {
				for(int k=1; k<=9; k++) {
					int brojac=0;
					for(int l=i; l<i+3; l++) {
						for(int m=j; m<j+3; m++) {
							if(element[l][m].getNumber()==0 && element[l][m].getUsed().contains(k)) brojac++;
						}
					}
					if(brojac==3) {
						for(int l=i; l<i+3; l++) {
							int brojac2=0;
							for(int m=j; m<j+3; m++) {
								if(element[l][m].getNumber()==0 && element[l][m].getUsed().contains(k)) brojac2++;
							}
							if(brojac2==3) {
								int promjena = 0;
								for(int m=0; m<N; m++) {
									Integer a = k;
									if(m<j || m>=j+3)
									if(element[l][m].getNumber()==0 && element[l][m].getUsed().contains((Object)a)) {
										element[l][m].getUsed().remove((Object)a);
										promjena++;
									}
								}
							if(promjena>0) {
								System.out.println("Posto u bloku sa pocetkom u ("+ i+","+j+") postoje tri elementa koja mogu biti broj " + k+ " i nalaze se u istom redu, znaci da je jedan od ta tri taj broj, a ostali iz tog reda mogu da se uklone" );
								break;
							}
							}
						}
						for(int l=j; l<j+3; l++) {
							int brojac2=0;
							for(int m=i; m<i+3; m++) {
								if(element[m][l].getNumber()==0 && element[m][l].getUsed().contains(k)) brojac2++;
							}
							if(brojac2==3) {
								int promjena = 0;
								for(int m=0; m<N; m++) {
									Integer a = k;
									if(m<i || m>=i+3)
									if(element[m][l].getNumber()==0 && element[m][l].getUsed().contains((Object)a)) {
										element[m][l].getUsed().remove((Object)a);
										promjena++;
									}
								}
								if(promjena>0) {
									System.out.println("Posto u bloku sa pocetkom u ("+ i+","+j+") postoje tri elementa koja mogu biti broj " + k+ " i nalaze se u istoj koloni, znaci da je jedan od ta tri taj broj, a ostali iz te kolone mogu da se uklone" );
									break;
								}
							}
							
						}
					}
				}
				
			}
		}
		return posljednjiSlobodan();
	}
	public int xKrilo() {
		List<ArrayList<Integer>> lista = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<9; i++) {
			lista.add(new ArrayList<Integer>());
		}
		for(int i=1; i<=9; i++) {
			for(int j=0; j<9; j++) {
				lista.get(j).clear();
			}
			for(int j=0; j<9; j++) {
				for(int k=0; k<9; k++) {
					if(element[j][k].getNumber()==0 && element[j][k].getUsed().contains(i)) {
						lista.get(j).add(k);
					}
				}
			}
			for(int j=0; j<9; j++) {
				if(lista.get(j).size()==2) {
					for(int k=j+1; k<9; k++) {
						if(lista.get(j).equals(lista.get(k))) {
							for(int l=0; l<9; l++) {
								if(l!=j && l!=k) {
									Integer broj = i;
									element[l][lista.get(k).get(0)].getUsed().remove((Object)broj);
									element[l][lista.get(k).get(1)].getUsed().remove((Object)broj);
									
								}
							}
							System.out.println("Pomocu X-wing tehnike uklonjeno u: " + j + " " + k + " element: " + i);
						}
					}
				}
			}
			for(int j=0; j<9; j++) {
				lista.get(j).clear();
			}
			for(int j=0; j<9; j++) {
				for(int k=0; k<9; k++) {
					if(element[k][j].getNumber()==0 && element[k][j].getUsed().contains(i)) {
						lista.get(j).add(k);
					}
				}
			}
			for(int j=0; j<9; j++) {
				if(lista.get(j).size()==2) {
					for(int k=j+1; k<9; k++) {
						if(lista.get(j).equals(lista.get(k))) {
							for(int l=0; l<9; l++) {
								if(l!=j && l!=k) {
									Integer broj = i;
									element[lista.get(k).get(0)][l].getUsed().remove((Object)broj);
									element[lista.get(k).get(1)][l].getUsed().remove((Object)broj);
									
								}
							}
							System.out.println("Pomocu X-wing tehnike uklonjeno u: " + j + " " + k + " element: " + i);
						}
					}
				}
			}
		}
		return posljednjiSlobodan();
	}
	public int yKrilo() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(element[i][j].getNumber()==0 && element[i][j].getUsed().size()==2) {
					int element1 = element[i][j].getUsed().get(0);
					int element2 = element[i][j].getUsed().get(1);
					Element prvi = null;
					int prviIndeksX=-1, prviIndeksY=-1;
					List<Integer> drugiUsed = new ArrayList<>();
					Integer zaUklanjanje=0;
					for(int k=0; k<9; k++) {
						for(int l=0; l<9; l++) {
							if((k!=i || l!=j) && element[k][l].getNumber()==0 && element[k][l].getUsed().size()==2 && !element[k][l].getUsed().equals(element[i][j].getUsed()) &&(element[k][l].getUsed().contains(element1) || element[k][l].getUsed().contains(element2)) &&vidiGa(i,j,k,l)) {
							
								if(prvi==null) {
									prvi = element[k][l];
									prviIndeksX= k;
									prviIndeksY= l;
									if(prvi.getUsed().contains(element1)) {
										drugiUsed = new ArrayList<>(prvi.getUsed());
										Integer ukloniti = element1;
										drugiUsed.remove((Object)ukloniti);
										zaUklanjanje = drugiUsed.get(0);
										drugiUsed.add(element2);
										Collections.sort(drugiUsed);
									}
									else {
										drugiUsed = new ArrayList<>(prvi.getUsed());
										Integer ukloniti = element2;
										drugiUsed.remove((Object)ukloniti);
										zaUklanjanje = drugiUsed.get(0);
										drugiUsed.add(element1);
										Collections.sort(drugiUsed);
									}
								}
								else {
									if(element[k][l].getUsed().equals(drugiUsed)) {
										
										for(int m=0; m<9; m++) {
											for(int n=0; n<9; n++) {
												if(element[m][n].getNumber()==0 && element[m][n].getUsed().size()==2  &&(m!=i || n!=j) && (m!=k || n!=l) && (m!=prviIndeksX || n!=prviIndeksY) && vidiGa(m,n,k,l) && vidiGa(m,n, prviIndeksX, prviIndeksY)) {
													System.out.println("Element na poziciji: (" + m + "," + n + ")"+ element[m][n].getUsed() + " zbog Y-wing tehnike ne moze biti " + zaUklanjanje);
													element[m][n].getUsed().remove((Object)zaUklanjanje);
													
													refresh();
												}
											}
										}
									}
								}
							}
						}
					}
					
				}
			}
		}
		return posljednjiSlobodan();
	}
	
	public boolean vidiGa(int x, int y, int x1, int y1) {
		if(x==x1 || y==y1 || ((x/3)*3==(x1/3)*3 && (y/3)*3==(y1/3)*3))return true;
		return false;
	}
	public int swordFish() {
		List<ArrayList<Integer>> lista = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<9; i++) {
			lista.add(new ArrayList<Integer>());
		}
		for(int i=1; i<=9; i++) {
			for(int j=0; j<9; j++) {
				lista.get(j).clear();
			}
			for(int j=0; j<9; j++) {
				for(int k=0; k<9; k++) {
					if(element[j][k].getNumber()==0 && element[j][k].getUsed().contains(i)) {
						lista.get(j).add(k);
					}
				}
			}
			for(int j=0; j<9; j++) {
				if(lista.get(j).size()==2) {
					int prvi = lista.get(j).get(0);
					int drugi = lista.get(j).get(1);
					for(int k=j+1; k<9; k++) {
						if(lista.get(k).size()==2 && !lista.get(j).equals(lista.get(k)) && (lista.get(k).contains(prvi) || lista.get(k).contains(drugi))) {
							List<Integer> drugiPar = new ArrayList<>();
							if(lista.get(k).contains(prvi)) {
								drugiPar.add(drugi);
								if(lista.get(k).get(0)==prvi) drugiPar.add(lista.get(k).get(1));
								else drugiPar.add(lista.get(k).get(0));
								Collections.sort(lista.get(k));
							}
							else {
								drugiPar.add(prvi);
								if(lista.get(k).get(0)==drugi) drugiPar.add(lista.get(k).get(1));
								else drugiPar.add(lista.get(k).get(0));
								Collections.sort(lista.get(k));
							}
							for(int l=k+1; l<9; l++) {
								if(lista.get(l).size()==2 && lista.get(l).equals(drugiPar)) {
									for(int m=0; m<9; m++) {
										if(m!=j && m!=k && m!=l) {
											Integer broj = i;
											element[m][lista.get(k).get(0)].getUsed().remove((Object)broj);
											element[m][lista.get(k).get(1)].getUsed().remove((Object)broj);
											element[m][lista.get(l).get(0)].getUsed().remove((Object)broj);
											element[m][lista.get(l).get(1)].getUsed().remove((Object)broj);
											element[m][lista.get(j).get(0)].getUsed().remove((Object)broj);
											element[m][lista.get(j).get(1)].getUsed().remove((Object)broj);
										}
									}
									System.out.println("Uklonjeno swordfish tehnikom u redovima:  " + j + " " + k + " " +l +" element:" + i);
								}
							}
 						}
					}
				}
			}
			for(int j=0; j<9; j++) {
				lista.get(j).clear();
			}
			for(int j=0; j<9; j++) {
				for(int k=0; k<9; k++) {
					if(element[k][j].getNumber()==0 && element[k][j].getUsed().contains(i)) {
						lista.get(j).add(k);
					}
				}
			}
			for(int j=0; j<9; j++) {
				if(lista.get(j).size()==2) {
					int prvi = lista.get(j).get(0);
					int drugi = lista.get(j).get(1);
					for(int k=j+1; k<9; k++) {
						if(lista.get(k).size()==2 && !lista.get(j).equals(lista.get(k)) && (lista.get(k).contains(prvi) || lista.get(k).contains(drugi))) {
							List<Integer> drugiPar = new ArrayList<>();
							if(lista.get(k).contains(prvi)) {
								drugiPar.add(drugi);
								if(lista.get(k).get(0)==prvi) drugiPar.add(lista.get(k).get(1));
								else drugiPar.add(lista.get(k).get(0));
								Collections.sort(lista.get(k));
							}
							else {
								drugiPar.add(prvi);
								if(lista.get(k).get(0)==drugi) drugiPar.add(lista.get(k).get(1));
								else drugiPar.add(lista.get(k).get(0));
								Collections.sort(lista.get(k));
							}
							for(int l=k+1; l<9; l++) {
								if(lista.get(l).size()==2 && lista.get(l).equals(drugiPar)) {
									for(int m=0; m<9; m++) {
										if(m!=j && m!=k && m!=l) {
											Integer broj = i;
											element[lista.get(k).get(0)][m].getUsed().remove((Object)broj);
											element[lista.get(k).get(1)][m].getUsed().remove((Object)broj);
											element[lista.get(j).get(0)][m].getUsed().remove((Object)broj);
											element[lista.get(j).get(1)][m].getUsed().remove((Object)broj);
											element[lista.get(l).get(0)][m].getUsed().remove((Object)broj);
											element[lista.get(l).get(1)][m].getUsed().remove((Object)broj);
										}
									}
									System.out.println("Uklonjeno swordfish tehnikom u kolonama " + j + " " + k + " " + l + " element: " + i);
								}
							}
 						}
					}
				}
			}
		}
		return posljednjiSlobodan();
	}
	public boolean proces() {
		int x=0;
		refresh();
		x = jediniMoguci();
		refresh();
		if(x==1) return true;
		x = posljednjiSlobodan();
		refresh();
		if(x==1) return true;
		x = ocigledniParovi();
		refresh();
		if(x==1) return true;
		x = ocigledneTrojke();
		refresh();
		if(x==1) return true;
		x = skriveniParovi();
		refresh();
		if(x==1) return true;
		x = pokazujuciParovi();
		refresh();
		if(x==1) return true;
		x = pokazujuceTrojke();
		refresh();
		if(x==1) return true;
		x = xKrilo();
		refresh();
		if(x==1) return true;
		x = yKrilo();
		refresh();
		if(x==1) return true;
		x = swordFish();
		refresh();
		if(x==1) return true;
		return false;
	}
}
