import java.util.HashMap;
import java.util.ArrayList;

	public class Dijkstra{

		private class DijkstraPair implements Comparable<DijkstraPair> 
		{
			String vname;
			String psf;
			int cost;

			@Override
			public int compareTo(DijkstraPair o) 
			{
				return o.cost - this.cost;
			}
		}

		public int dijkstra(String src, String des, boolean nan) 
		{
			int val = 0;
			ArrayList<String> ans = new ArrayList<>();
			HashMap<String, DijkstraPair> map = new HashMap<>();

			Heap<DijkstraPair> heap = new Heap<>();

			for (String key : Graph_M.vtces.keySet()) 
			{
				DijkstraPair np = new DijkstraPair();
				np.vname = key;
				//np.psf = "";
				np.cost = Integer.MAX_VALUE;

				if (key.equals(src)) 
				{
					np.cost = 0;
					np.psf = key;
				}

				heap.add(np);
				map.put(key, np);
			}

			//keep removing the pairs while heap is not empty
			while (!heap.isEmpty()) 
			{
				DijkstraPair rp = heap.remove();
				
				if(rp.vname.equals(des))
				{
					val = rp.cost;
					break;
				}
				
				map.remove(rp.vname);

				ans.add(rp.vname);
				
				Graph_M.Vertex v = Graph_M.vtces.get(rp.vname);
				for (String nbr : v.nbrs.keySet()) 
				{
					if (map.containsKey(nbr)) 
					{
						int oc = map.get(nbr).cost;
						Graph_M.Vertex k = Graph_M.vtces.get(rp.vname);
						int nc;
						if(nan)
							nc = rp.cost + 120 + 40*k.nbrs.get(nbr);
						else
							nc = rp.cost + k.nbrs.get(nbr);

						if (nc < oc) 
						{
							DijkstraPair gp = map.get(nbr);
							gp.psf = rp.psf + nbr;
							gp.cost = nc;

							heap.updatePriority(gp);
						}
					}
				}
			}
			return val;
		}
	}