import { Category } from './Category';
export class Product{
   id?: number;
  name?: string;
  description?: string;
  price?: number;
  image?: string;
  imageBytes?: any;
  categoryId?: number;
  categoryDTO?: Category;
  imageUrl?: string | null; // ✅ Add this
}
